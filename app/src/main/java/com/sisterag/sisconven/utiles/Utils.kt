package com.sisterag.sisconven.utiles

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns
import android.view.View

import com.google.android.material.snackbar.Snackbar
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

fun View.snackbar(message: String) {
    Snackbar.make(
        this,
        message,
        Snackbar.LENGTH_LONG
    ).also { snackbar ->
        snackbar.setAction("ok") {
            snackbar.dismiss()
        }
    }.show()
}

fun createImagePart(imageFile: File): MultipartBody.Part {
    val requestBody = RequestBody.create("image/*".toMediaTypeOrNull(), imageFile)
    return MultipartBody.Part.createFormData("image", imageFile.name, requestBody)
}

@SuppressLint("Range")
fun ContentResolver.getFileName(uri: Uri): String {
    var name: String = ""
    val cursor = query(
        uri, null, null,
        null, null
    )
    cursor?.use {
        it.moveToFirst()
        name = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
    }
    return name
}