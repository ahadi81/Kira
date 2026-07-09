package com.ahadi.kira.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahadi.kira.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance("https://kira-d4d82-default-rtdb.firebaseio.com/")

    // Single source of truth for the logged-in user profile
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    // NEW: Single source of truth for the entire user list (tenants)
    private val _allUsers = MutableStateFlow<List<User>>(emptyList())
    val allUsers: StateFlow<List<User>> = _allUsers.asStateFlow()

    // NEW: Tracks if the database list is currently fetching
    private val _isLoadingUsers = MutableStateFlow(false)
    val isLoadingUsers: StateFlow<Boolean> = _isLoadingUsers.asStateFlow()

    init {
        auth.currentUser?.uid?.let { uid ->
            fetchUserData(uid)
        }
    }

    fun signup(
        firstname: String,
        lastname: String,
        phonenum: String,
        email: String,
        password: String,
        role: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val result = auth.createUserWithEmailAndPassword(email, password).await()
                val userid = result.user?.uid ?: throw Exception("Failed to create account!")

                val user = User(
                    id = userid,
                    firstname = firstname,
                    lastname = lastname,
                    phonenum = phonenum,
                    email = email,
                    role = role
                )

                db.getReference("users")
                    .child(userid)
                    .setValue(user)
                    .await()

                _currentUser.value = user
                onResult(true, null)
            } catch (e: Exception) {
                onResult(false, e.localizedMessage)
            }
        }
    }

    fun login(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            try {
                val result = auth.signInWithEmailAndPassword(email, password).await()
                val userid = result.user?.uid ?: throw Exception("Login failed!")

                // NEW: Fetch profile immediately on success so the home screen has the name
                fetchUserData(userid)
                onResult(true, null)
            } catch (e: Exception) {
                onResult(false, e.localizedMessage)
            }
        }
    }

    private fun fetchUserData(userId: String) {
        viewModelScope.launch {
            try {
                val snapshot = db.getReference("users").child(userId).get().await()
                val user = snapshot.getValue(User::class.java)?.copy(id = snapshot.key ?: "")
                _currentUser.value = user
            } catch (e: Exception) {
                _currentUser.value = null
            }
        }
    }

    private var isListeningToUsers = false

    // NEW: Listens to the entire 'users' database path in real-time
    fun fetchAllUsers() {
        if (isListeningToUsers) return // Don't start multiple listeners
        
        _isLoadingUsers.value = true
        isListeningToUsers = true

        viewModelScope.launch {
            try {
                db.getReference("users")
                    .addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val users = mutableListOf<User>()
                            for (child in snapshot.children) {
                                val user = child.getValue(User::class.java)
                                user?.let { users.add(it.copy(id = child.key ?: "")) }
                            }
                            _allUsers.value = users
                            _isLoadingUsers.value = false
                        }

                        override fun onCancelled(error: DatabaseError) {
                            android.util.Log.e("KiraApp", "Failed to fetch users: ${error.message}")
                            _isLoadingUsers.value = false
                        }
                    })
            } catch (e: Exception) {
                android.util.Log.e("KiraApp", "Error fetching users: ${e.localizedMessage}")
                _isLoadingUsers.value = false
            }
        }
    }






    fun addTenantByLandlord(
        firstname: String,
        lastname: String,
        phonenum: String,
        email: String,
        apartmentname: String,
        housenum: String,
        role: String = "tenant",
        onResult: (Boolean, String?) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val landlordId = auth.currentUser?.uid
                    ?: return@launch onResult(false, "No landlord logged in")

                val sanitizedEmailKey = email.replace(".", ",").replace("@", "-")

                val newTenant = User(
                    id = sanitizedEmailKey,
                    firstname = firstname,
                    lastname = lastname,
                    phonenum = phonenum,
                    email = email,
                    role = role,
                    apartmentname = apartmentname,
                    housenum = housenum,
                    landlordId =  landlordId  // ← ADD THIS
                )

                db.getReference("users")
                    .child(sanitizedEmailKey)
                    .setValue(newTenant)
                    .await()

                onResult(true, null)
            } catch (e: Exception) {
                onResult(false, e.localizedMessage)
            }
        }
    }

}
