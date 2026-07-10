package com.ahadi.kira.ui.theme.screens.homescreens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAlert
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.House
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.ahadi.kira.navigation.DashboardItem
import com.ahadi.kira.navigation.ROUTE_TENANTALT
import com.ahadi.kira.ui.theme.Blue1
import com.ahadi.kira.ui.theme.Brown1
import com.ahadi.kira.ui.theme.components.ModernDashboardCard
import com.ahadi.kira.viewmodel.AuthViewModel


@Composable
fun tenanthome(navController: NavController,
               authViewModel: AuthViewModel,

               ) {
    val userState by authViewModel.currentUser.collectAsStateWithLifecycle()
    var mContext = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp)
    ) {
        // Top Header Section
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Hello ${userState?.firstname}! " 
                            ,
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
                    imageVector = Icons.Default.House,
                    contentDescription = "House",
                    tint = Brown1
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
//
//        Text("Landlord name: YOUR LANDLORD NAME.",
//           fontFamily = FontFamily.Serif,
//            fontWeight = FontWeight.ExtraBold
//        )

        Spacer(modifier = Modifier.height(16.dp))

        // Grid Menu
        val dashboardItems = listOf(
            DashboardItem("Apartment", "${userState?.apartmentname}", Icons.Filled.Apartment, Blue1, {}),
//            DashboardItem("Add tenant", "new tenant", Icons.Default.PersonAdd, Brown1, onClick = {}),
            DashboardItem("Alert to landlord.", "send.", Icons.Filled.AddAlert, Brown1,{navController.navigate(ROUTE_TENANTALT)}),
//            DashboardItem("Rent collected", "<the value>", Icons.Default.Payments, Blue1,{}),
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(dashboardItems) { item ->
                ModernDashboardCard(item)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))


        LazyColumn() { }

        Button(
            onClick = {
                val intent = mContext.packageManager.getLaunchIntentForPackage("com.android.stk")
                intent?.let { mContext.startActivity(it) }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(Blue1),
            elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 8.dp)
        ) {
            Text(text = "Pay Rent")

        }
    }


//@SuppressLint("ViewModelConstructorInComposable")


}
