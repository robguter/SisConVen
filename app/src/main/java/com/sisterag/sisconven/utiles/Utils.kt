package com.sisterag.sisconven.utiles

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.OpenableColumns
import android.view.View
import android.widget.ImageView
import android.widget.Toast

import com.google.android.material.snackbar.Snackbar
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream

fun loadImageFromTempFile(tempFile: File, imageView: ImageView) {
    try {
        val bitmap = BitmapFactory.decodeFile(tempFile.absolutePath)
        imageView.setImageBitmap(bitmap)
    } catch (e: Exception) {
        // Handle the error (e.g., log it, display a placeholder)
        e.printStackTrace()
    }
}
fun MessageOwn(context: Context?, msj: String) {
    Toast.makeText(context, "" + msj, Toast.LENGTH_LONG).show()
}
fun fileFromContentUri(context: Context, uri: Uri): File? {
    var extns = getFileExtension(uri, context)
    if(extns == "jpeg") {
        extns = "jpg"
    }
    val tempFile = File.createTempFile(extns.toString(), null, context.cacheDir)
    try {
        val inputStream = context.contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(tempFile)
        inputStream?.copyTo(outputStream)
        return tempFile
    } catch (e: Exception) {
        e.printStackTrace()
        tempFile.delete()
        return null
    }
}

fun getFileExtension(uri: Uri, context: Context): String? {
    val contentResolver: ContentResolver = context.contentResolver
    val mimeType = contentResolver.getType(uri)
    if (mimeType != null) {
        return mimeType.split("/")[1]
    }
    return null
}
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