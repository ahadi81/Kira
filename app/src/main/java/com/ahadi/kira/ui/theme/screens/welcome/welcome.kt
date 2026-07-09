package com.ahadi.kira.ui.theme.screens.welcome

import androidx.compose.foundation.Image
//import androidx.compose.foundation.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahadi.kira.R

import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ahadi.kira.navigation.ROUTE_LOGIN
import com.ahadi.kira.navigation.ROUTE_SIGNINLANDLORD
import com.ahadi.kira.navigation.ROUTE_SIGNINTENANT
import com.ahadi.kira.ui.theme.Blue1
import com.ahadi.kira.ui.theme.Brown1

@Composable

fun welcome(navController: NavHostController) {
    val scrollState = rememberScrollState()
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(color = Brown1)
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(10.dp)


    ) {
Image(
    painter = painterResource(R.drawable.apartment),
    contentDescription = "apartment",
    modifier = Modifier.size(200.dp)
)
        Text(
            "Kira.",
            color = Blue1,
            fontStyle = FontStyle.Italic,
            fontSize = 25.sp,
            fontWeight = FontWeight.ExtraBold
            )
                    Spacer(modifier = Modifier.height(2.dp))
          Text(          " Keep track of all your rent.",
            color = Color.White,
            fontStyle = FontStyle.Italic,
            fontSize = 20.sp
        )

        Button(
            onClick = {navController.navigate(ROUTE_SIGNINLANDLORD)},
            modifier = Modifier.width(300.dp),
            colors = ButtonDefaults.buttonColors(Blue1)
        ) { Text("I am a landlord.") }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {navController.navigate(ROUTE_SIGNINTENANT)},
            modifier = Modifier.width(300.dp),
            colors = ButtonDefaults.buttonColors(Blue1)
        ) { Text("I am a tenant.") }




        Button(onClick ={
            navController.navigate(ROUTE_LOGIN)},

            modifier = Modifier.width(300.dp),



            colors = ButtonDefaults.buttonColors(Blue1)) {

            Text("Already have an account? Log in!")

        }
    }


//    Spacer(modifier = Modifier.height(50.dp))
//
//    Button(onClick ={
//navController.navigate(ROUTE_LOGIN)},
//
//            modifier = Modifier.width(300.dp),
//        colors = ButtonDefaults.buttonColors(Blue1)) {
//
//        Text("Already have an account? Log in!")
//
//    }
}



//



//Kira
//
//This is a rent tracking app. We are supposed to use the app to do this:;
//1.Landlord can see the list of tenants.
//2.Landlord can communicate to individual tenants concerning arrears. (Alert sent to tenant)
//3.Tenant can communicate with the landlord about issues that need repairs(sends alert to the landlord)
//4.Tenant can see the payment history of the tenant.
//5.Landlord can then delete a profile for a tenant when the tenant moves out.
//6. the landlord then updates a list of who has paid and what they paid and then both can see it in a page called payment history/details.


@Preview
@Composable

 private fun welcomescreen() {
    welcome(rememberNavController())

//i absolutely love coding and i am learning the syntax through these errors, halelujah
}