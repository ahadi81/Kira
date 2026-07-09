package com.ahadi.kira.models

import com.google.firebase.Timestamp

data class Tenantalt(
    val alertId: String="",
    val tenantId: String="",
    val landlordId: String="",
    val apartmentname: String="",
    val housenum: String="",
    val alert: String="",
    val imageUrl:String="",
    val timestamp: Long=0L,
    val status:String="unread"








    )

data class  LandlordAlert(
    val alertId: String="",
    val landlordId: String="",
    val tenantId: String = "",  // empty string means "all tenants"
    val housenum: String = "",
    val message: String = "",
    val timestamp: Long = 0L
)