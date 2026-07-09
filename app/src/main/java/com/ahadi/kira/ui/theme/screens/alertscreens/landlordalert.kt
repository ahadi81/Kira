package com.ahadi.kira.ui.theme.screens.alertscreens

import android.app.Person
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NoteAlt
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ahadi.kira.navigation.ROUTE_LANDLORDHM
import com.ahadi.kira.ui.theme.Blue1
import com.ahadi.kira.ui.theme.Brown1
import com.ahadi.kira.viewmodel.AlertViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun landlordalert(navController: NavController,
                  alertViewModel: AlertViewModel = viewModel()
 ){
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val tenants by alertViewModel.tenants.collectAsState()
    val isLoading by alertViewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        alertViewModel.fetchMyTenants()
    }



   Column(verticalArrangement = Arrangement.Top,
       horizontalAlignment = Alignment.CenterHorizontally,
       modifier = Modifier
           .fillMaxSize()
           .background(Color.White)
           .verticalScroll(scrollState)
           .padding(24.dp)
           .imePadding()
           ) {

       Spacer(modifier = Modifier.height(40.dp))

        var isButtonClicked by remember { mutableStateOf(false) }
       var houseno by remember { mutableStateOf("") }
       var tenantname by remember { mutableStateOf("") }
       var alert by remember { mutableStateOf("") }
//       val houseno by remember { mutableStateOf(TextFieldValue("")) }


       Text("Send out alerts to your tenants.",
           color = Blue1,
           fontStyle = FontStyle.Italic,
           fontFamily = FontFamily.Monospace,



       )

       OutlinedTextField(
           value = houseno,
           onValueChange = { houseno = it },
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
       Spacer(modifier = Modifier.height(30.dp))



       var expanded by remember { mutableStateOf(false) }

       ExposedDropdownMenuBox(
           expanded = expanded,
           onExpandedChange = { expanded = !expanded }
       ) {
           OutlinedTextField(
               value = tenantname,
               onValueChange = {},
               readOnly = true,
               textStyle = TextStyle(Color.Black),
               label = { Text("Select recipient") },
               leadingIcon = {Icon(Icons.Default.PersonAdd, contentDescription = "add a tenant", tint = Brown1)},
               trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
               modifier = Modifier.menuAnchor().width(300.dp)
           )

           ExposedDropdownMenu(
               expanded = expanded,
               onDismissRequest = { expanded = false }
           ) {
               DropdownMenuItem(
                   text = { Text("All Tenants") },
                   onClick = {
                       tenantname = "All Tenants"
                       expanded = false
                   }
               )
               tenants.forEach { tenant ->
                   DropdownMenuItem(
                       text = {Text ("${tenant.firstname} ${tenant.lastname}")},
                       onClick = {
                           tenantname = "${tenant.firstname} ${tenant.lastname}"
                           expanded = false
                       }
                   )
               }
           }
       }
       Spacer(modifier = Modifier.height(30.dp))



       OutlinedTextField(
           value = alert,
           onValueChange = { alert = it },
           textStyle = TextStyle(Color.Black),
           leadingIcon = {
               Icon(
                   Icons.Default.NoteAlt,
                   contentDescription = null,
                   tint = Brown1
               )
           },
           label = {
               Text(
                   "Enter alert to tenant/tenants.",
                   color = Color.Black,
                   fontSize = 16.sp,
                   fontFamily = FontFamily.SansSerif
               )
           },
           modifier = Modifier.width(300.dp)
       )
       Spacer(modifier = Modifier.height(30.dp))

//       Button(
//           onClick = {
//               // Handle your submit logic here (e.g., validate form, send data)
//               println("Sent succesfully!")
//           },
//           modifier = Modifier.padding(top = 16.dp)
//       ) {
//           Text(text = "Submit")
//       }

       Button(onClick = {
           if (tenantname.isBlank() || alert.isBlank() || houseno.isBlank()) {
               Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_LONG).show()
               return@Button
           }
           alertViewModel.sendLandlordAlert(
               housenum = houseno,
               message = alert,
               selectedTenant = tenantname,
               tenants = tenants,
               onResult = { success, error ->
                   if (success) {
                       Toast.makeText(context, "Alert sent!", Toast.LENGTH_SHORT).show()
                       navController.navigate(ROUTE_LANDLORDHM)
                   } else {
                       Toast.makeText(context, "Failed: $error", Toast.LENGTH_LONG).show()
                   }
               }
           )
       },
           modifier = Modifier.width(300.dp)
       ) {
           Text("Send.")
       }

       if (isButtonClicked){
           Text("Sent succesfully!",
               color = Color.Black,
               fontSize = 40.sp,
               fontWeight = FontWeight.ExtraBold,
               fontStyle = FontStyle.Italic,
               fontFamily = FontFamily.Monospace,

               )






       }








   }


}

@Preview
@Composable
private fun LandlordAlertPreview(){
    landlordalert(rememberNavController())
}
