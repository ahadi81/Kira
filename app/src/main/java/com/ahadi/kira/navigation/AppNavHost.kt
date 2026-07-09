package com.ahadi.kira.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ahadi.kira.ui.theme.screens.addtenant.addtenantScreen
import com.ahadi.kira.ui.theme.screens.alertscreens.TenantAlert
//import com.ahadi.kira.ui.theme.screens.alerts.confirmalert
import com.ahadi.kira.ui.theme.screens.alertscreens.landlordalert
import com.ahadi.kira.ui.theme.screens.alertscreens.TenantAlert
//import com.ahadi.kira.ui.theme.screens.homescreens.landlordhm
import com.ahadi.kira.ui.theme.screens.homescreens.landlordhome
import com.ahadi.kira.ui.theme.screens.signup.loginLandlord
//import com.ahadi.kira.ui.theme.screens.signup.logintenant
import com.ahadi.kira.ui.theme.screens.homescreens.tenanthome
import com.ahadi.kira.ui.theme.screens.login.LoginScreen
import com.ahadi.kira.ui.theme.screens.registration.propertyregscreen
import com.ahadi.kira.ui.theme.screens.signup.signupten
import com.ahadi.kira.ui.theme.screens.welcome.welcome
import com.ahadi.kira.viewmodel.AuthViewModel

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ROUTE_WELCOME
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(ROUTE_WELCOME) {
            welcome(navController)
        }
        composable(ROUTE_SIGNINLANDLORD) {
            loginLandlord(navController)
        }
        composable(ROUTE_SIGNINTENANT) {
            signupten(navController)
        }
        composable(ROUTE_TENANTHOME) {
            val authViewModel: AuthViewModel = viewModel()
            tenanthome(navController, authViewModel)
        }
        composable(ROUTE_LANDLORDHM) {
            val authViewModel: AuthViewModel = viewModel()
            landlordhome(navController, authViewModel)
        }
        composable(ROUTE_LANDLORDALT) {
            landlordalert(navController)
        }

        composable(ROUTE_TENANTALT){
            TenantAlert(navController)
        }

        composable (ROUTE_PROPERTYREG){
            propertyregscreen(navController)
        }

        composable (ROUTE_ADDTENANT){
            val authViewModel: AuthViewModel = viewModel()
            addtenantScreen(navController, authViewModel)
        }

        composable (ROUTE_LOGIN) {
            val authViewModel: AuthViewModel = viewModel()
            LoginScreen(navController, authViewModel)
        }

    }
}
