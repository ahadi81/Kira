package com.ahadi.kira.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
//import com.cloudinary.android.MediaManager
//import com.cloudinary.android.callback.ErrorInfo
//import com.cloudinary.android.callback.UploadCallback
//import androidx.navigation.internal.NavContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class UploadStatus {
    object Idle : UploadStatus()
    object Loading : UploadStatus()
    data class Success (val url : String) : UploadStatus()
    data class Error(val message : String) : UploadStatus()
}
class ImageUploadViewModel : ViewModel(){
    private val _uploadState = MutableStateFlow<UploadStatus>(UploadStatus.Idle)
    val uploadState: StateFlow<UploadStatus> = _uploadState.asStateFlow()

    fun uploadUnsignedImage(uri: Uri,context: Context){
//        _uploadState.value = UploadStatus.Loading
//        MediaManager.get().upload(uri)
//            .unsigned("kira_app")
//            .callback(object : UploadCallback {
//                override fun onStart(requestId: String?) {
//
//                }
//
//                override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {
//
//                }
//
//                override fun onSuccess(requestId: String?, resultData: Map<*, *>?) {
//
//                    val url = resultData?.get("secure_url") as? String
//                    if(url != null) {
//                        _uploadState.value = UploadStatus.Success(url)
//
//                    } else  {
//                        _uploadState.value = UploadStatus.Error("URL not returned")
//                    }
//
//                }
//
//                override fun onError(
//                    requestId: String?,
//                    error: ErrorInfo?
//                ) {
//_uploadState.value = UploadStatus.Error(error?.description?: "Upload failed!")                }
//
//                override fun onReschedule(
//                    requestId: String?,
//                    error: ErrorInfo?
//                ) {
//                    _uploadState.value= UploadStatus.Error("Could not complete right now, try again later.")
//                }
//            })
//            .dispatch()
    }

}