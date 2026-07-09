package com.ahadi.kira.ui.theme.screens.homescreens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ahadi.kira.models.Tenantalt
import com.ahadi.kira.navigation.DashboardItem
import com.ahadi.kira.navigation.ROUTE_ADDTENANT
import com.ahadi.kira.navigation.ROUTE_LANDLORDALT
import com.ahadi.kira.navigation.ROUTE_TENANTALT
import com.ahadi.kira.navigation.ROUTE_WELCOME
import com.ahadi.kira.ui.theme.Blue1
import com.ahadi.kira.ui.theme.Brown1
import com.ahadi.kira.ui.theme.components.ModernDashboardCard
import com.ahadi.kira.viewmodel.AlertViewModel
import com.ahadi.kira.viewmodel.AuthViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun landlordhome(
    navController: NavController,
    authViewModel: AuthViewModel,
    alertViewModel: AlertViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val userState by authViewModel.currentUser.collectAsStateWithLifecycle()
    val isLoadingAlerts by alertViewModel.isLoading.collectAsStateWithLifecycle()
    val alerts by alertViewModel.alerts.collectAsStateWithLifecycle()

    val mContext = LocalContext.current

    // Start listening for alerts the moment the screen loads
    LaunchedEffect(Unit) {
        alertViewModel.fetchAlertsForLandlord()
    }

    val dashboardItems = remember {
        listOf(
            DashboardItem("Add tenant", "New", Icons.Filled.PersonAddAlt1, Blue1, { navController.navigate(ROUTE_ADDTENANT) }),
//            DashboardItem("House No", "A4", Icons.Default.Numbers, Brown1, onClick = {}),
            DashboardItem("Alerts to tenants.", "send.", Icons.Filled.AddAlert, Brown1, { navController.navigate(
                ROUTE_LANDLORDALT
            ) }),
//            DashboardItem("payment history", "Paid", Icons.Default.Payments, Blue1, {}),
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp)
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Item 1: Top Welcome Header
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "hello, ${userState?.firstname ?: "Landlord"} !",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Blue1
                        )
                    }
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(Brown1.copy(alpha = 0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Apartment,
                            contentDescription = "apartment",
                            tint = Brown1
                        )
                    }
                }
            }

            // Item 2: Property Name
            item {
                Text(
                    text = userState?.apartmentname ?: "Property Dashboard",
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.ExtraBold
                )
            }

            // Item 3: Dashboard Grid
            item {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    dashboardItems.chunked(2).forEach { rowItems ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            rowItems.forEach { item ->
                                Box(modifier = Modifier.weight(1f)) {
                                    ModernDashboardCard(item)
                                }
                            }
                            if (rowItems.size == 1) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
            }

            // Item 4: Alerts Section Header
            item {
                Text(
                    text = "Tenant Alerts",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Blue1,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            // Item 5: Alerts List
            if (isLoadingAlerts) {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Blue1)
                    }
                }
            } else if (alerts.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No alerts from tenants yet.",
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                    }
                }
            } else {
                items(
                    items = alerts,
                    key = { alert -> alert.alertId }
                ) { alert ->
                    AlertListItem(alert = alert)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                navController.navigate(ROUTE_WELCOME)
               }
            ,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(Blue1),
            elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 8.dp)
        ) {
            Text(text = "log out?")
        }
    }
}

@Composable
fun AlertListItem(alert: Tenantalt) {
    val formattedTime = remember(alert.timestamp) {
        SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
            .format(Date(alert.timestamp))
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (alert.status == "unread") Color(0xFFFFF3E0) else Color(0xFFF5F5F5)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = alert.apartmentname,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Brown1
                )
                Text(
                    text = "House ${alert.housenum}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = alert.alert,
                fontSize = 14.sp,
                color = Color.DarkGray
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = formattedTime,
                fontSize = 12.sp,
                color = Color.Gray
            )
            if (alert.status == "unread") {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "● NEW",
                    fontSize = 11.sp,
                    color = Brown1,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview
@Composable
private fun landlordhomeprev() {
    landlordhome(rememberNavController(), authViewModel = AuthViewModel())
}