package com.sisterag.sisconven.utiles

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.core.content.ContentProviderCompat.requireContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class UriToFile(context: Context) {

    private val applicationContext = context.applicationContext

    fun getImageBody(imageUri: Uri): File {
        val parcelFileDescriptor = applicationContext.contentResolver.openFileDescriptor(
            imageUri,
            "r",
            null
        )
        val file = File(
            applicationContext.cacheDir,
            applicationContext.contentResolver.getFileName(imageUri)
        )
        val inputStream = FileInputStream(parcelFileDescriptor?.fileDescriptor)
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)
        return file
    }
}

// Calling the api
private fun updateUserProfile() {

    /*val file = UriToFile(requireContext()).getImageBody(theUri)
    val requestFile: RequestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())

    val builder: MultipartBody.Builder = MultipartBody.Builder().setType(MultipartBody.FORM)

    builder.addFormDataPart("first_name", firstName) // whatever data you will pass to the the request body
        .addFormDataPart("profile_photo", file.name, requestFile) // the profile photo
    // make sure the name (ie profile_photo), matches your api, that is name of the key.


    val requestBody: RequestBody = builder.build()

    // pass the request body to make your retrofit call
    viewModel.updateUserProfile(requestBody).observe(viewLifecycleOwner){
    }*/
}