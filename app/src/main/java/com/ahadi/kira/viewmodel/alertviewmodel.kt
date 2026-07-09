package com.ahadi.kira.viewmodel

import android.os.Message
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahadi.kira.data.User
import com.ahadi.kira.models.LandlordAlert
import com.ahadi.kira.models.Tenantalt
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

class AlertViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance("https://kira-d4d82-default-rtdb.firebaseio.com/")

    private val _alerts = MutableStateFlow<List<Tenantalt>>(emptyList())
    val alerts: StateFlow<List<Tenantalt>> = _alerts.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    private val _tenants = MutableStateFlow<List<User>>(emptyList())
//    private val tenant = MutableStateFlow<List<User>>(emptyList())
val tenants: StateFlow<List<User>> = _tenants.asStateFlow()

    fun submitAlert(
        apartmentname: String,
        housenum: String,
        alert: String,
        imageUrl: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val tenantId = auth.currentUser?.uid
                    ?: return@launch onResult(false, "Not logged in")

                // Read tenant's profile to get their landlordId
                val tenantSnapshot = db.getReference("users")
                    .child(tenantId)
                    .get()
                    .await()

                val landlordId = tenantSnapshot.child("landlordId").getValue(String::class.java)
                    ?: return@launch onResult(false, "No landlord linked to this account")

                // Auto-generate a unique alert ID
                val alertRef = db.getReference("alerts")
                    .child(landlordId)
                    .push()

                val tenantAlert = Tenantalt(
                    alertId = alertRef.key ?: "",
                    tenantId = tenantId,
                    landlordId = landlordId,
                    apartmentname = apartmentname,
                    housenum = housenum,
                    alert = alert,
                    imageUrl = imageUrl,
                    timestamp = System.currentTimeMillis(),
                    status = "unread"
                )

                alertRef.setValue(tenantAlert).await()
                onResult(true, null)

            } catch (e: Exception) {
                onResult(false, e.localizedMessage)
            }
        }
    }

    fun fetchAlertsForLandlord() {
        val landlordId = auth.currentUser?.uid ?: return

        _isLoading.value = true

        db.getReference("alerts")
            .child(landlordId)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val alertList = mutableListOf<Tenantalt>()
                    for (child in snapshot.children) {
                        val item = child.getValue(Tenantalt::class.java)
                        item?.let { alertList.add(it) }
                    }
                    _alerts.value = alertList.sortedByDescending { it.timestamp }
                    _isLoading.value = false
                }

                override fun onCancelled(error: DatabaseError) {
                    _isLoading.value = false
                }

            })
    }


    fun fetchMyTenants(){
        val landlordId = auth.currentUser?.uid?: return

        _isLoading.value = true
        db.reference.child("users")
            .orderByChild("landlordId")
            .equalTo(landlordId)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val tenantlist = mutableListOf<User>()
                    for (child in snapshot.children) {
                        val item = child.getValue(User::class.java)
                        item?.let { tenantlist.add(it) }
                    }
                    _tenants.value = tenantlist
                    _isLoading.value = false
                }
                        override fun onCancelled(error: DatabaseError) {
                            _isLoading.value = false
                        }


                    




            })




    }

    fun sendLandlordAlert(
        housenum: String,
        message: String,
        selectedTenant: String,  // either "All Tenants" or a specific tenant email
        tenants: List<User>,
        onResult: (Boolean, String?) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val landlordId = auth.currentUser?.uid
                    ?: return@launch onResult(false, "Not logged in")

                val targets = if (selectedTenant == "All Tenants") {
                    tenants  // write to all tenants
                } else {
                    tenants.filter { "${it.firstname} ${it.lastname}" == selectedTenant }
                }

                for (tenant in targets) {
                    val ref = db.getReference("landlordAlerts")
                        .child(tenant.email.replace(".", ",").replace("@", "-"))
                        .push()

                    val alert = LandlordAlert(
                        alertId = ref.key ?: "",
                        landlordId = landlordId,
                        tenantId = tenant.email,
                        housenum = housenum,
                        message = message,
                        timestamp = System.currentTimeMillis()
                    )
                    ref.setValue(alert).await()
                }
                onResult(true, null)
            } catch (e: Exception) {
                onResult(false, e.localizedMessage)
            }
        }
    }




}

