package com.ahadi.kira.ui.theme.screens.addtenant

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material3.Button
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ahadi.kira.ui.theme.Brown1
import com.ahadi.kira.viewmodel.AuthViewModel

@Composable
fun addtenantScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    var firstname by remember { mutableStateOf("") }
    var lastname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phonenum by remember { mutableStateOf("") }
    var apartmentname by remember { mutableStateOf("") }
    // Changed to var so Compose can dynamically update the text input
    var housenum by remember { mutableStateOf("") }

    val scrollstate = rememberScrollState()
    var isSaving by remember { mutableStateOf(false) }
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
            text = "Add a new tenant.",
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            color = Brown1,
        )

        Spacer(modifier = Modifier.height(32.dp))

        // 1. First Name Field
        OutlinedTextField(
            value = firstname,
            onValueChange = { firstname = it },
            textStyle = TextStyle(Color.Black),
            leadingIcon = {
                Icon(
                    Icons.Default.PersonOutline,
                    contentDescription = null,
                    tint = Brown1
                )
            },
            label = {
                Text(
                    "Enter tenant first name",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontFamily = FontFamily.SansSerif
                )
            },
            modifier = Modifier.width(300.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 2. Last Name Field
        OutlinedTextField(
            value = lastname,
            onValueChange = { lastname = it },
            textStyle = TextStyle(Color.Black),
            leadingIcon = {
                Icon(
                    Icons.Default.PersonOutline,
                    contentDescription = null,
                    tint = Brown1
                )
            },
            label = {
                Text(
                    "Enter tenant last name",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontFamily = FontFamily.SansSerif
                )
            },
            modifier = Modifier.width(300.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 3. Phone Number Field
        OutlinedTextField(
            value = phonenum,
            onValueChange = { phonenum = it },
            textStyle = TextStyle(Color.Black),
            leadingIcon = {
                Icon(
                    Icons.Default.PersonOutline,
                    contentDescription = null,
                    tint = Brown1
                )
            },
            label = {
                Text(
                    "Enter tenant's phone number",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontFamily = FontFamily.SansSerif
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.width(300.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 4. Email Field
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            textStyle = TextStyle(Color.Black),
            leadingIcon = {
                Icon(
                    Icons.Default.Email,
                    contentDescription = null,
                    tint = Brown1
                )
            },
            label = {
                Text(
                    "Enter email",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontFamily = FontFamily.SansSerif
                )
            },
            modifier = Modifier.width(300.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 6. House Number Field
        OutlinedTextField(
            value = housenum,
            onValueChange = { housenum = it },
            textStyle = TextStyle(Color.Black),
            leadingIcon = {
                Icon(
                    Icons.Default.Numbers,
                    contentDescription = null,
                    tint = Brown1
                )
            },
            label = {
                Text(
                    "Enter house number",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontFamily = FontFamily.SansSerif
                )
            },
            modifier = Modifier.width(300.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 7. Save Action Button Configuration
        if (isSaving) {
            CircularProgressIndicator(color = Brown1)
        } else {
            Button(
                onClick = {
                    if (firstname.isNotBlank() && email.isNotBlank()) {
                        isSaving = true
                        authViewModel.addTenantByLandlord(
                            firstname = firstname,
                            lastname = lastname,
                            phonenum = phonenum,
                            email = email,
                            apartmentname = apartmentname,
                            housenum = housenum
                        ) { success, error ->
                            isSaving = false
                            if (success) {
                                Toast.makeText(context, "Tenant added successfully", Toast.LENGTH_SHORT).show()
                                navController.popBackStack()
                            } else {
                                Toast.makeText(context, "Error: $error", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                },
                modifier = Modifier.width(300.dp)
            ) {
                Text("Add tenant.")
            }
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview
@Composable
private fun addtenantscreenprev() {
    addtenantScreen(rememberNavController(), AuthViewModel())
}





