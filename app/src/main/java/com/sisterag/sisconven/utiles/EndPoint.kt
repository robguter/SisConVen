package com.sisterag.sisconven.utiles

import android.content.Context

import android.graphics.Bitmap
import android.graphics.BitmapFactory

import android.os.Environment

import android.view.View.OnFocusChangeListener
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast

import androidx.constraintlayout.widget.ConstraintLayout

import coil.load
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.math.RoundingMode
import java.text.DecimalFormat


object EndPoint {

    const val BASE_URL: String = "https://www.sisterag.com/"
    // "http://172.16.0.8:8787/VentasSon/"

    const val IMG_URL: String = "https://www.sisterag.com/pubs/img/"
    // "http://172.16.0.8:8787/VentasSon/pubs/img/"
    /** VENTA **/
    const val GetAllVntaUrl: String = "venta/getAll/"
    const val GetVntaXidUrl: String = "venta/getById/{id}"
    const val GetVntaNmbUrl: String = "proveedor/getByNombre/{nombre}"
    const val AddVntaUrl: String = "venta/insert/"
    const val ActVntaUrl: String = "venta/update/{id}"
    const val DelVntaUrl: String = "venta/delete/{id}"
    /** CATEGORIA **/
    const val GetAllCateUrl: String = "categoria/getAll/"
    const val GetCateXidUrl: String = "categoria/getById/{id}"
    const val GetCateNmbUrl: String = "proveedor/getByNombre/{nombre}"
    const val AddCateUrl: String = "categoria/insert/"
    const val ActCateUrl: String = "categoria/update/{id}"
    const val DelCateUrl: String = "categoria/delete/{id}"
    /** CLIENTE **/
    const val GetAllCteUrl: String = "cliente/getAll/"
    const val GetCteXidUrl: String = "cliente/getById/{id}"
    const val GetCteNmbUrl: String = "proveedor/getByNombre/{nombre}"
    const val AddCteUrl: String = "cliente/insert/"
    const val ActCteUrl: String = "cliente/update/{id}"
    const val DelCteUrl: String = "cliente/delete/{id}"
    /** ARTICULOS **/
    const val GetAllArtUrl: String = "articulos/getAll/"
    const val GetArtXidUrl: String = "articulos/getById/{id}"
    const val GetArtNmbUrl: String = "proveedor/getByNombre/{nombre}"
    const val AddArtUrl: String =    "articulos/insert/"
    const val AddArtUrli: String =   "articulos/subeimg/"
    const val ActArtUrl: String =    "articulos/update/{id}"
    const val DelArtUrl: String =    "articulos/delete/{id}"
    /** CTAXCOB **/
    const val GetAllCtaXcobUrl: String = "ctaxcob/getAll/"
    const val GetCtaXcobXidUrl: String = "ctaxcob/getById/{id}"
    const val GetCtaXcobNmbUrl: String = "proveedor/getByNombre/{nombre}"
    const val AddCtaXcobUrl: String = "ctaxcob/insert/"
    const val ActCtaXcobUrl: String = "ctaxcob/update/{id}"
    const val DelCtaXcobUrl: String = "ctaxcob/delete/{id}"
    /** COMPRA **/
    const val GetAllCpraUrl: String = "compra/getAll/"
    const val GetCpraXidUrl: String = "compra/getById/{id}"
    const val GetCpraXidProvUrl: String = "compra/getByIdC/{idprov}"
    const val GetCpraNmbUrl: String = "compra/getByNombre/{nombre}"
    const val AddCpraUrl: String = "compra/insert/"
    const val ActCpraUrl: String = "compra/update/{id}"
    const val DelCpraUrl: String = "compra/delete/{id}"
    /** PEDIDO **/
    const val GetAllPedUrl: String = "pedido/getAll/"
    const val GetPedXidUrl: String = "pedido/getById/{id}"
    const val GetPedXidCteUrl: String = "pedido/getByIdC/{idprov}"
    const val GetPedNmbUrl: String = "pedido/getByNombre/{nombre}"
    const val AddPedUrl: String = "pedido/insert/"
    const val ActPedUrl: String = "pedido/update/{id}"
    const val DelPedUrl: String = "pedido/delete/{id}"
    /** PROVEEDOR **/
    const val GetAllProvUrl: String = "proveedor/getAll/"
    const val GetProvXidUrl: String = "proveedor/getById/{id}"
    const val GetProvNmbUrl: String = "proveedor/getByNombre/{nombre}"
    const val AddProvUrl: String = "proveedor/insert/"
    const val ActProvUrl: String = "proveedor/update/{id}"
    const val DelProvUrl: String = "proveedor/delete/{id}"
    /** CTAXPAG **/
    const val GetAllCtaXpagUrl: String = "ctaxpag/getAll/"
    const val GetCtaXpagXidUrl: String = "ctaxpag/getById/{id}"
    const val GetCtaXpagNmbUrl: String = "ctaxpag/getByNombre/{nombre}"
    const val AddCtaXpagUrl: String = "ctaxpag/insert/"
    const val ActCtaXpagUrl: String = "ctaxpag/update/{id}"
    const val DelCtaXpagUrl: String = "ctaxpag/delete/{id}"
    /** IVA **/
    const val GetAllIvaUrl: String = "iva/getAll/"
    const val GetIvaXidUrl: String = "iva/getById/{id}"
    const val GetIvaNmbUrl: String = "iva/getByNombre/{nombre}"
    const val AddIvaUrl: String = "iva/insert/"
    const val ActIvaUrl: String = "iva/update/{id}"
    const val DelIvaUrl: String = "iva/delete/{id}"
    /** VARIOS **/
    const val GetAllVarUrl: String = "varios/getAll/"
    const val GetVarXidUrl: String = "varios/getById/{id}"
    const val GetVarNmbUrl: String = "varios/getByNombre/{nombre}"
    const val AddVarUrl: String = "varios/insert/"
    const val ActVarUrl: String = "varios/update/{id}"
    const val DelVarUrl: String = "varios/delete/{id}"

    fun verdad (bTrue: Boolean): Int {
        return when(bTrue) {
            true-> {
                1
            }
            else -> {
                0
            }
        }
    }
    fun comprueba(ll:ConstraintLayout, context: Context): Boolean {
        // Obtiene el numero de EditText que contiene el layout
        val count = ll.childCount
        // Recorres todos los editText y si hay alguno vacio cambias el valor de la
        // variable isAllFill a false, lo que indica que aun hay editText vacios.
        var isAllFill = true
        for (i in 0 until count) {
            // En cada iteración obtienes uno de los editText que se encuentran el
            // layout.
            val et :EditText
            val oObj = ll.getChildAt(i)
            if (oObj is EditText) {
                et = oObj

                if (et.text.toString().isEmpty()) {
                    isAllFill = false
                    break
                }
            }
        }
        if(!isAllFill) {
            Toast.makeText(
                context, "Hay Campos Vacios",
                Toast.LENGTH_LONG
            ).show()
        }
        return isAllFill
    }
    fun addListeners(recBoton:Button, context: Context) {
        recBoton.setOnFocusChangeListener(OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                Toast.makeText(
                    context, "Focus gained",
                    Toast.LENGTH_LONG
                    ).show()
            } else {
                Toast.makeText(
                    context, "Focuse released",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
    fun BuscaEspacio(string: String): String {
        val s = string.split(' ')[0].trim()
        return s.trim()
    }
    fun formatNum(num: Double): String {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        return df.format(num)
    }
    fun getImageFromStorage(context: Context, imagePath: String): Bitmap? {
        return try {
            val inputStream: InputStream = context.assets.open(imagePath)
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    fun saveImageToStorage(context: Context, bitmap: Bitmap, filename: String): File? {
        val directory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val file = File(directory, filename)
        return try {
            val fileOutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
            fileOutputStream.close()
            file
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    fun displayImage(context: Context, imageView: ImageView, imagePath: String) {
        val bitmap = getImageFromStorage(context, imagePath)
        imageView.setImageBitmap(bitmap)
    }
    fun loadImageFromUrl(imageView: ImageView, imageUrl: String) {
        imageView.load(imageUrl) {
            crossfade(true)
            // Add error and placeholder as needed
        }
    }

}