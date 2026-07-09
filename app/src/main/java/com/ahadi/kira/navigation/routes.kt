package com.ahadi.kira.navigation

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

const val ROUTE_WELCOME="welcome"
const val ROUTE_SIGNINLANDLORD="signinord"
const val ROUTE_SIGNINTENANT="signintenant"


const val ROUTE_TENANTHOME="tenanthome"

//const val ROUTE_PAYMENTDET="paymentdetails"

const val ROUTE_LANDLORDHM = "landlordhome"
const val ROUTE_TENANTALT = "tenantalert"
const val ROUTE_LANDLORDALT = "landlordalert"
data class DashboardItem(
    val title: String,
    val value: String,
    val icon: ImageVector,
    val color: Color,
    val onClick: (() -> Unit?)?
)

const val ROUTE_ADDTENANT = "addtenant"


const val ROUTE_LOGIN="login"



//const val ROUTE_CONFIRMALT = "confirmalert"


const val ROUTE_PROPERTYREG="propertyreg"


