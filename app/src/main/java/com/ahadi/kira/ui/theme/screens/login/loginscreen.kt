package com.ahadi.kira.ui.theme.screens.login



import android.R
import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ahadi.kira.navigation.ROUTE_LANDLORDHM
import com.ahadi.kira.navigation.ROUTE_TENANTHOME
import com.ahadi.kira.ui.theme.Blue1
import com.ahadi.kira.ui.theme.Brown1
import com.ahadi.kira.viewmodel.AuthViewModel


@Composable
fun LoginScreen(navController: NavHostController,
                authViewModel: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
//    val context = LocalContext.current
    val currentUserState by authViewModel.currentUser.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White)
            .verticalScroll(scrollState)
            .padding(24.dp)
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = "Welcome Back",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Blue1
        )

        Text(
            text = "Login to your account",
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(Color.Black),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Email",
                    tint = Brown1
                )
            },
            label = { Text(" Enter email Address",
                color = Color.Black,
                fontSize =16.sp,
                fontFamily = FontFamily.SansSerif

                ) },
            shape = RoundedCornerShape(12.dp),
           colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Blue1,                focusedLabelColor = Blue1,
               cursorColor = Blue1
           )
        )

        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            textStyle = TextStyle(Color.Black),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Password,
                    contentDescription = "Password",
                    tint = Brown1
                )
            },
            label = {
                Text("Enter password",
                    color = Color.Black,
                     fontSize = 16.sp,
                    fontFamily = FontFamily.SansSerif
                ) },
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Blue1,
                focusedLabelColor = Blue1,
                cursorColor = Blue1
            )
        )

        errorMessage?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = it, color = Color.Red, fontSize = 14.sp)
        }

        Spacer(modifier = Modifier.height(32.dp))

        if (isLoading) {
            CircularProgressIndicator(color = Blue1)
        } else{
            Button(
                onClick={
                    if (email.isNotBlank() && password.isNotBlank()) {
                        isLoading = true
                        errorMessage=null

                        authViewModel.login(email,password){success, error ->
                            isLoading = false
                            if (success){
                                if(currentUserState?.role == "landlord"){
                                    navController.navigate(ROUTE_LANDLORDHM){
                                        popUpTo("login"){inclusive=true}
                                    }
                                }else  {
                                    navController.navigate(ROUTE_TENANTHOME){
                                        popUpTo ("login"){inclusive = true}
                                    }
                                }

                            }else {
                                errorMessage = error ?: "An unknown error occured"
                            }
                        }
                    }else {
                        errorMessage = "Please fill in all fields correctly."
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Blue1)
            ) {
                Text("Log in!")
            }
        }
    }
}



@SuppressLint("ViewModelConstructorInComposable")
@Preview
@Composable
private fun login(){
    LoginScreen(rememberNavController(),AuthViewModel())
}