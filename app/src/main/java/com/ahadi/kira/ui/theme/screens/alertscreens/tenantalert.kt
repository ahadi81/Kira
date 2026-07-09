package com.ahadi.kira.ui.theme.screens.alertscreens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
//import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.NoteAlt
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.tooling.preview.Preview
import com.ahadi.kira.navigation.ROUTE_TENANTHOME
import com.ahadi.kira.ui.theme.Blue1
import com.ahadi.kira.ui.theme.Brown1
import com.ahadi.kira.viewmodel.AlertViewModel
//import com.ahadi.kira.viewmodel.ImageUploadViewModel
//import com.ahadi.kira.viewmodel.UploadStatus
//import com.cloudinary.android.MediaManager


@Composable
fun TenantAlert(
    navController: NavHostController,
    alertViewModel: AlertViewModel = viewModel(),
//    imageUploadViewModel: ImageUploadViewModel =viewModel()
) {
    val context = LocalContext.current
    val isLoading by alertViewModel.isLoading.collectAsState()
//    val uploadState by imageUploadViewModel.uploadState.collectAsState()
////    val uploadState by imageUploadViewModel.isUploading.collectAsState()
//    val launcher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.GetContent()
//    ) { result ->
////        whatever goes inside the launcher
//        result?.let {
//            imageUploadViewModel.uploadUnsignedImage(uri = it, context = context)
//
//        }
////        imageUploadViewModel.uploadUnsignedImage(uri = Uri,context: Context)
//
//
//
//    }
    var apartmentName by remember { mutableStateOf("") }
    var houseNumber by remember { mutableStateOf("") }
    var complaint by remember { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        Text("Make alert to landlord")

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = apartmentName,
            onValueChange = { apartmentName = it },
            textStyle = TextStyle(Color.Black),
            leadingIcon = {
                Icon(
                    Icons.Default.Apartment,
                    contentDescription = null,
                    tint = Brown1
                )
            },
            label = {
                Text(
                    "Enter apartment name",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontFamily = FontFamily.SansSerif
                )
            },
            modifier = Modifier.width(300.dp)
        )
        Spacer(modifier = Modifier.height(30.dp))

        OutlinedTextField(
            value = houseNumber,
            onValueChange = { houseNumber  = it },
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

        OutlinedTextField(
            value = complaint,
            onValueChange = { complaint = it },
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
                    "Enter alert/complaint.",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontFamily = FontFamily.SansSerif
                )
            },
            modifier = Modifier.width(300.dp)
        )

//        Button(onClick = {
//            launcher.launch("image/*")
//        },
//            modifier = Modifier.width(300.dp))  {
//            Text(
//                when (uploadState) {
//                    is UploadStatus.Idle -> "Pick a picture for the alert"
//                    is UploadStatus.Loading -> "Uploading picture..."
//                    is UploadStatus.Success -> "Picture uploaded successfully!"
//                    is UploadStatus.Error -> "Upload failed! Try again"
//                }
//            )
//        }
//
//        if (uploadState is UploadStatus.Loading) {
//            Spacer(modifier = Modifier.height(8.dp))
//            CircularProgressIndicator(modifier = Modifier.size(24.dp))
//        }
//
//        if (uploadState is UploadStatus.Error){
//            Text(
//                text = (uploadState as UploadStatus.Error).message,
//                color = Color.Red,
//                fontSize = 12.sp,
//                modifier = Modifier.padding(top = 4.dp)
//            )
//        }

        Spacer(modifier = Modifier.height(30.dp))

        if (isLoading) {
            CircularProgressIndicator(color = Blue1)
        } else {
            Button(onClick = {
                // Basic validation — don't submit empty fields
                if (apartmentName.isBlank() || houseNumber.isBlank() || complaint.isBlank()) {
                    Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                    return@Button
                }
//                if (uploadState is UploadStatus.Success){
//                    val imageUrl = (uploadState as UploadStatus.Success).url
                    alertViewModel.submitAlert(
                        apartmentname = apartmentName,
                        housenum = houseNumber,
                        alert = complaint,
                        imageUrl = "", // No image
                        onResult = { success, error ->
                            if (success) {
                                Toast.makeText(context, "Alert sent!", Toast.LENGTH_SHORT).show()
                                navController.navigate(ROUTE_TENANTHOME)
                            } else {
                                Toast.makeText(context, "Failed: $error", Toast.LENGTH_LONG).show()
                            }
                        }
                    )
//                } else if (uploadState is UploadStatus.Loading) {
//                    Toast.makeText(context, "Still uploading, please wait...", Toast.LENGTH_SHORT).show()
//                } else {
//                    Toast.makeText(context, "Please upload an image first.", Toast.LENGTH_LONG).show()
//                }


            },
                modifier = Modifier.width(300.dp)) {
                Text("Send .")
            }
        }
    }
}

@Preview
@Composable
private fun TenantAltPrev() {
    TenantAlert(rememberNavController(),
        )
}
