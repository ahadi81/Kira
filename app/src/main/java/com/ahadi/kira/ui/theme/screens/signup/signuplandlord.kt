package com.ahadi.kira.ui.theme.screens.signup

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ahadi.kira.navigation.ROUTE_LANDLORDHM
import com.ahadi.kira.navigation.ROUTE_PROPERTYREG
import com.ahadi.kira.ui.theme.Blue1
import com.ahadi.kira.ui.theme.Brown1
import com.ahadi.kira.viewmodel.AuthViewModel

@Composable
fun loginLandlord(navController: NavHostController) {
    var firstname by remember { mutableStateOf(TextFieldValue("")) }
    var lastname by remember { mutableStateOf(TextFieldValue("")) }

    var email by remember {mutableStateOf(TextFieldValue(""))}
    var phonenum by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var confirmpass by remember { mutableStateOf(TextFieldValue("")) }
    val scrollstate = rememberScrollState()
    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(scrollstate)
            .padding(24.dp)
            .imePadding()
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Register as a landlord",
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            color = Brown1,
        )
        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = firstname,
            onValueChange = { firstname = it },
            textStyle = TextStyle(Color.Black),
            leadingIcon = { Icon(Icons.Default.PersonOutline, contentDescription = null, tint = Brown1) },
            label = {
                Text(
                    "Enter first name",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontFamily = FontFamily.SansSerif
                )
            },
            modifier = Modifier.width(300.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = lastname,
            onValueChange = { lastname = it },
            textStyle = TextStyle(Color.Black),
            leadingIcon = { Icon(Icons.Default.PersonOutline, contentDescription = null, tint = Brown1) },
            label = {
                Text(
                    "Enter last name",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontFamily = FontFamily.SansSerif
                )
            },
            modifier = Modifier.width(300.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))



        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            textStyle = TextStyle(Color.Black),
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = Brown1) },
            label = {
                Text(
                    "Enter email address",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontFamily = FontFamily.SansSerif
                )
            },
            modifier = Modifier.width(300.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = phonenum,
            onValueChange = { phonenum = it },
            textStyle = TextStyle(Color.Black),
            leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null, tint = Brown1) },
            label = {
                Text(
                    "Enter Phone number",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontFamily = FontFamily.SansSerif
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.width(300.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))


        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            textStyle = TextStyle(Color.Black),
            visualTransformation = PasswordVisualTransformation(),
            leadingIcon = { Icon(Icons.Default.Password, contentDescription ="password", tint = Brown1) },
            label = {
                Text(
                    "Enter password",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontFamily = FontFamily.SansSerif
                )
            },
            modifier = Modifier.width(300.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = confirmpass,
            onValueChange = { confirmpass = it },
            visualTransformation = PasswordVisualTransformation(),
            textStyle = TextStyle(Color.Black),
            leadingIcon = { Icon(Icons.Default.Password,contentDescription = "confirmpassword", tint = Brown1) },
            label = {
                Text(
                    "Confirm password",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontFamily = FontFamily.SansSerif
                )
            },
            modifier = Modifier.width(300.dp)
        )
        Spacer(modifier = Modifier.height(48.dp))
        Spacer(modifier = Modifier.height(20.dp))



        Button(
            onClick = {
                 if (password.text != confirmpass.text) {
                     Toast.makeText(context, "Passwords do not match", Toast.LENGTH_LONG).show()
                 } else {
                     val viewModel = AuthViewModel()
                     viewModel.signup(
                         firstname=firstname.text,
                         lastname=lastname.text,
                         phonenum=phonenum.text,
                         email=email.text,
                         password=password.text,
                         role="landlord",
                         onResult = {success,error ->
                             isLoading= false
                             if (success) {

                                 Toast.makeText(context,"registered as a landlord!",Toast.LENGTH_LONG).show()
                                 navController.navigate(ROUTE_PROPERTYREG)
//                                 popUpTo(0){inclusive=true}
                             } else {
                                 Toast.makeText(context,error?:"Try again", Toast.LENGTH_LONG).show()
                             }
                         }
                     )
                 }


                      },
            enabled = !isLoading,

            modifier = Modifier.width(300.dp),
            colors = ButtonDefaults.buttonColors(Blue1)

        ) {


            if (isLoading){
                CircularProgressIndicator(
                    color = Blue1,
                    strokeWidth = 2.dp,
                    modifier = Modifier.size(18.dp)
                )
            }
            Text("Register")
        }
        
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginLandlordPreview() {
    loginLandlord(rememberNavController())
}
