package com.ahadi.kira.viewmodel

//import android.util.Property
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahadi.kira.models.Property
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PropertyViewModel : ViewModel(){
    private val db = FirebaseDatabase.getInstance()
    private val auth = FirebaseAuth.getInstance()

    fun addProperty(
        apartmentname:String,
        location:String,
        rentpm:String,
        unitsavailable: String,
       onResult: (Boolean, String?) -> Unit
    ){
        viewModelScope.launch {
            try {
                val currentUserId =auth.currentUser?.uid ?: throw Exception ("landlord not registered!")

                val propertyRef = db.getReference("properties").push()
                val uniquePropertyId = propertyRef.key?: throw Exception("Failed to register property!")

                val property = Property(
                    propertyId = uniquePropertyId,
                    apartmentname = apartmentname,
                    location = location,
                    rentpm = rentpm,
                    unitsavailable = unitsavailable,
                    landlordId = currentUserId,
                )

                propertyRef.setValue(property).await()
                onResult(true,null)

            }catch (e: Exception){
                onResult(false, e.localizedMessage)
            }
        }
    }

}