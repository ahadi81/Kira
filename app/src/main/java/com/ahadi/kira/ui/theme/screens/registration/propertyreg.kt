package com.ahadi.kira.ui.theme.screens.registration

import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.EditLocation
import androidx.compose.material.icons.filled.House
import androidx.compose.material.icons.filled.Payments
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ahadi.kira.navigation.ROUTE_LANDLORDHM
import com.ahadi.kira.ui.theme.Blue1
import com.ahadi.kira.ui.theme.Brown1
import com.ahadi.kira.viewmodel.PropertyViewModel

@Composable
fun propertyregscreen(
    navController: NavHostController,
    propertyViewModel: PropertyViewModel = viewModel() // FIX 3: Properly added the ViewModel instance here
) {
    // FIX 1: Switched from TextFieldValue to standard Strings (much easier to manage and validate)
    var apartmentname by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var rentpm by remember { mutableStateOf("") }
    var unitsavailable by remember { mutableStateOf("") }

    val ScrollState = rememberScrollState()
    val context = LocalContext.current // FIX 2: Corrected the syntax from ";" to "."
    var isLoading by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(ScrollState)
            .padding(24.dp)
            .imePadding()
    ) {

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Welcome to Kira!",
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.W500,
            fontStyle = FontStyle.Italic,
            fontSize = 14.sp,
            color = Blue1,
        )
        Text(
            text = "Register your property details to get started.",
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            color = Brown1,
            modifier = Modifier.padding(top = 4.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))


        OutlinedTextField(
            value = apartmentname,
            onValueChange = { apartmentname = it },
            textStyle = TextStyle(Color.Black),
            leadingIcon = { Icon(Icons.Default.Apartment, contentDescription = null, tint = Brown1) },
            label = { Text("Enter property name", color = Color.Black, fontSize = 16.sp) },
            modifier = Modifier.width(300.dp),
            enabled = !isLoading
        )
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = location,
            onValueChange = { location = it },
            textStyle = TextStyle(Color.Black),
            leadingIcon = { Icon(Icons.Default.EditLocation, contentDescription = null, tint = Brown1) },
            label = { Text("Enter property location", color = Color.Black, fontSize = 16.sp) },
            modifier = Modifier.width(300.dp),
            enabled = !isLoading
        )
        Spacer(modifier = Modifier.height(12.dp))


        OutlinedTextField(
            value = rentpm,
            onValueChange = { rentpm = it },
            textStyle = TextStyle(Color.Black),
            leadingIcon = { Icon(Icons.Default.Payments, contentDescription = null, tint = Brown1) },
            label = { Text("Enter rent per month", color = Color.Black, fontSize = 16.sp) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.width(300.dp),
            enabled = !isLoading
        )
        Spacer(modifier = Modifier.height(12.dp))


        OutlinedTextField(
            value = unitsavailable,
            onValueChange = { unitsavailable = it },
            textStyle = TextStyle(Color.Black),
            leadingIcon = { Icon(Icons.Default.House, contentDescription = null, tint = Brown1) },
            label = { Text("Enter units available for renting", color = Color.Black, fontSize = 16.sp) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.width(300.dp),
            enabled = !isLoading
        )
        Spacer(modifier = Modifier.height(19.dp))


        Button(
            onClick = {
                if (apartmentname.isBlank() || location.isBlank() || rentpm.isBlank() || unitsavailable.isBlank()) {
                    Toast.makeText(context, "Please fill in all details.", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                isLoading = true

                propertyViewModel.addProperty(
                    apartmentname = apartmentname,
                    location = location,
                    rentpm = rentpm,
                    unitsavailable = unitsavailable,
                    onResult = { isSuccess, errorMessage ->
                        isLoading = false

                        if (isSuccess) {
                            Toast.makeText(context, "Property registered successfully", Toast.LENGTH_LONG).show()
                            navController.navigate(ROUTE_LANDLORDHM) {
                                // Clears registration out of the backstack so back press won't re-submit data
                                popUpTo(ROUTE_LANDLORDHM) { inclusive = true }
                            }
                        } else {
                            Toast.makeText(context, "Error: $errorMessage", Toast.LENGTH_LONG).show()
                        }
                    }
                )
            },
            modifier = Modifier.width(300.dp),
            colors = ButtonDefaults.buttonColors(Blue1),
            enabled = !isLoading
        ) {

            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = Brown1,
                    strokeWidth = 2.dp
                )
            } else {
                Text("Register Property")
            }
        }
    }
}



@Preview
@Composable

private fun propertyregscreenprev(){
    propertyregscreen(rememberNavController())
}
