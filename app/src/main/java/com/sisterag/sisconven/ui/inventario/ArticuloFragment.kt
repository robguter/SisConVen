package com.sisterag.sisconven.ui.inventario

import android.annotation.SuppressLint
import android.app.Activity

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle

import android.provider.MediaStore
import android.provider.OpenableColumns

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts

import androidx.compose.runtime.Composable

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.FileProvider

import androidx.fragment.app.Fragment

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sisterag.sisconven.clients.RetrofitClient
import com.sisterag.sisconven.databinding.FragmentArticuloBinding
import com.sisterag.sisconven.modelos.inventario.ArticuloDto
import com.sisterag.sisconven.modelos.inventario.UploadRequestBody
import com.sisterag.sisconven.modelos.inventario.UploadResponse
import com.sisterag.sisconven.servicios.ApiServiceArt
import com.sisterag.sisconven.servicios.MiAPI
import com.sisterag.sisconven.utiles.EndPoint.BASE_URL

import com.sisterag.sisconven.utiles.EndPoint.comprueba
import com.sisterag.sisconven.utiles.URIPathHelper
import com.sisterag.sisconven.utiles.UriToFile
import com.sisterag.sisconven.utiles.createImagePart
import com.sisterag.sisconven.utiles.snackbar
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

import java.io.IOException

/**
 * A simple [Fragment] subclass.
 * Use the [ArticuloFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ArticuloFragment : Fragment(), UploadRequestBody.UploadCallBack {

    val apiService = RetrofitClient.create(ApiServiceArt::class.java)
    val TAG: String = ArticuloFragment::class.java.simpleName
    private lateinit var artList: List<ArticuloDto>
    private lateinit var resp: String
    private var binding: FragmentArticuloBinding? = null
    private lateinit var rvwArt: RecyclerView

    private lateinit var txtArt: EditText
    private lateinit var txtIdc: EditText
    private lateinit var txtDesc: EditText
    private lateinit var txtMarc: EditText
    private lateinit var txtImag: EditText
    private lateinit var btnUpload: Button
    private lateinit var image: File

    private lateinit var sIdct: String
    private lateinit var sArti: String
    private lateinit var sMarc: String
    private lateinit var sDesc: String
    private lateinit var sImag: String

    private lateinit var rvadapter: ArtAdapter
    private lateinit var ivImage: ImageView
    private lateinit var pbProgress: ProgressBar
    private lateinit var lLayout: ConstraintLayout
    private var selectedImage: Uri? = null

    // This property is only valid between onCreateView and
    // onDestroyView.

    private lateinit var context: Context
    private var mCurrentPhotoPath: String? = null
    private var mPhotoURI: Uri? = null

    val contentResolver = activity?.contentResolver
    lateinit var imageUri: Uri

    private lateinit var bitmap: Bitmap

    private val contract = registerForActivityResult(ActivityResultContracts.GetContent()) {
        imageUri = it!!
        ivImage.setImageURI(it)
        //main()
    }

    private val pickMedia = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { result ->
        if (result != null) {
            selectedImage = result
            imageUri = selectedImage as Uri
            ivImage.setImageURI(result)
            //image = getFileFromUri(result)
            //main()
            Log.i("PATHPATH", result.pathSegments.toString())
        }else{
            Log.i("TAGTAG", "No seleccionada")
        }
    }
    fun main() = runBlocking { // Esto crea una corrutina
        //createImageFile(context) // Llamada desde una corrutina
    }

    private fun showCameraApp(context: Context) {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        if (intent.resolveActivity(context.packageManager) != null) {
            var photoFile: File? = null
            try {
                //photoFile = main()// createImageFile(context)
            } catch (ex: IOException) {
                Log.d(
                    TAG,
                    "Error ocurrido cuando se estaba creando el archivo de la imagen. Detalle: $ex"
                )
            }

            if (photoFile != null) {
                mPhotoURI = FileProvider.getUriForFile(
                    context,
                    "com.hermosaprogramacion.blog.saludmock.fileprovider",
                    photoFile
                )
                intent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoURI)
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    private fun createImageFormData(imageFile: File, partName: String = "image"): MultipartBody.Part {
        val requestFile = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
        image = imageFile
        return MultipartBody.Part.createFormData(partName, imageFile.name, requestFile)
    }
    /************************************/

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArticuloBinding.inflate(inflater, container, false)
        val root: View = binding!!.root
        binding!!.pbProgress.visibility = View.INVISIBLE

        lLayout = binding!!.linearLayout

        context = requireActivity().applicationContext
        pbProgress = binding!!.pbProgress
        rvwArt = binding!!.rvwRecyclr
        txtArt = binding!!.etArticulo
        txtArt.setOnClickListener(View.OnClickListener {
            rvwArt.apply {
                rvwArt.setAdapter(null)
            }
        })
        txtIdc = binding!!.etIdcat
        txtIdc.setOnClickListener(View.OnClickListener {
            rvwArt.apply {
                rvwArt.setAdapter(null)
            }
        })
        txtDesc = binding!!.etDescripcion
        txtDesc.setOnClickListener(View.OnClickListener {
            rvwArt.apply {
                rvwArt.setAdapter(null)
            }
        })
        txtMarc = binding!!.etMarca

        ivImage = binding!!.ivImage

        ivImage.setOnClickListener(View.OnClickListener {
            rvwArt.apply {
                rvwArt.setAdapter(null)
            }
            //cargaImg()
            escogeImgn()
            //contract.launch("image/*")
        })

        btnUpload = binding!!.btnUpload
        btnUpload.setOnClickListener(View.OnClickListener {

        })

        val btnEnvi: Button = binding!!.btnGuardar
        btnEnvi.setOnClickListener(View.OnClickListener {
            val bValor: Boolean = comprueba(lLayout, this.context)
            if(!bValor) {
                return@OnClickListener
            }
            binding!!.pbProgress.visibility = View.VISIBLE
            upImageSrvr(image)
        })

        artList = listOf()

        GlobalScope.launch(Dispatchers.IO) {
            //val apiService = RetrofitClient.create(ApiServiceArt::class.java)
            val response = try {
                apiService.getArtAll()
            }catch (e: IOException){
                Log.d("APP Error", "${e.message} "+" ${e.localizedMessage}")
                return@launch
            }catch (e: HttpException){
                Log.d("HTTP Error", "${e.message}")
                return@launch
            }
            if(response.isSuccessful && response.body() != null) {
                withContext(Dispatchers.Main) {
                    artList = response.body()!!
                    val rvwArt = binding!!.rvwRecyclr
                    rvwArt.apply {
                        rvadapter = ArtAdapter(artList)

                        rvwArt.setLayoutManager(LinearLayoutManager(activity))
                        rvwArt.setHasFixedSize(true)
                        adapter = rvadapter
                        rvwArt.setAdapter(adapter)
                    }
                }
            }else{
                Log.d("Error Retrofit", "${response.code()} - ${response.message()}")
            }
        }
        return root
    }

    @Composable
    private fun subeImage() {
    }

    private fun escogeImgn() {
        Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
            val mimeTypes = arrayOf("image/jpeg", "image/png")
            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            startActivityForResult(it, REQUEST_CODE_IMAGE)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_IMAGE && data != null) {
            when(requestCode) {
                REQUEST_CODE_IMAGE -> {
                    selectedImage = data.data
                    imageUri = selectedImage!!
                    ivImage.setImageURI(imageUri)
                    image = UriToFile(context).getImageBody(imageUri)
                    Log.i("ImagePICK", imageUri.toString())
                }
            }
            try {
                val inputStream: ContentResolver? = contentResolver
                val bitmap: Bitmap? = try {
                    if (inputStream != null){
                        BitmapFactory.decodeStream(inputStream.openInputStream(imageUri))
                    } else {
                        null
                    }

                }catch (e: Exception){
                    e.printStackTrace()
                    null
                }
                bitmap?.let { ivImage.setImageBitmap(it) }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    @SuppressLint("Recycle")
    private fun ContentResolver.getFileName(selectedUri: Uri): String {
        var name = ""
        val returnCursor = selectedImage?.let { this.query(it, null, null, null, null) }
        if(returnCursor != null) {
            val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            returnCursor.moveToFirst()
            name = returnCursor.getString((nameIndex))
            returnCursor.close()
        }
        return name
    }

    private fun cargaImg() {
        pickMedia.launch(
            PickVisualMediaRequest(
                ActivityResultContracts.PickVisualMedia.ImageOnly
            )
        )
    }
    fun upImageSrvr(imageFile: File) {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL) // Reemplaza con la URL de tu servidor
            .addConverterFactory(GsonConverterFactory.create()) // Opcional, para deserializar JSON
            .build()

        val imageService = retrofit.create(MiAPI::class.java)
        val imagePart = createImagePart(imageFile)
            /*image
                image.name,
                RequestBody.create("image*".toMediaTypeOrNull(), image)
            )*/
        sIdct = txtIdc.text.toString()
        sArti = txtArt.text.toString()
        sMarc = txtMarc.text.toString()
        sDesc = txtDesc.text.toString()
        val requestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("idcat", sIdct)
            .addFormDataPart("articulo", sArti)
            .addFormDataPart("marca", sMarc)
            .addFormDataPart("descripcion", sDesc)
            .addFormDataPart("image",image.toString())

            .build()

        val call = imageService.uploadImage(requestBody)

        call.enqueue(object: Callback<UploadResponse> { // Reemplaza UploadResponse con tu clase de respuesta

            override fun onResponse(p0: Call<UploadResponse>, p1: Response<UploadResponse>) {
                if (p1.isSuccessful) {
                    // Manejar la respuesta exitosa
                    println("Imagen subida con éxito: ${p1.body()}")
                } else {
                    // Manejar la respuesta con error
                    println("Error al subir la imagen: ${p1.errorBody()}")
                }
            }

            override fun onFailure(p0: Call<UploadResponse>, p1: Throwable) {
                // Manejar el error de la solicitud
                println("Error al realizar la solicitud: ${p1.message}")
            }

        })
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun postRequest(context: Context) {
        //val apiService = RetrofitClient.create(ApiServiceArt::class.java)
        sIdct = txtIdc.text.toString()
        val iIdct = sIdct.toInt()
        sArti = txtArt.text.toString()
        sMarc = txtMarc.text.toString()
        sDesc = txtDesc.text.toString()
        //createImageFile(context)

        binding!!.etIdcat.text.clear()
        binding!!.etArticulo.text.clear()
        binding!!.etMarca.text.clear()
        binding!!.etDescripcion.text.clear()

        // Create a RequestBody for the image file
        val imageBody = image.asRequestBody(
            "image/*".toMediaTypeOrNull()
        )
        val imagePart = createImageFormData(image)

        GlobalScope.launch(Dispatchers.IO) {
            try {

                val sImage:String = image.name
                val requestBody: RequestBody = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("Idcat", sIdct)
                    .addFormDataPart("Articulo", sArti)
                    .addFormDataPart("Marca", sMarc)
                    .addFormDataPart("Descripcion", sDesc)
                    .addFormDataPart("image", sImage)
                    .build()

                val response = MiAPI.uploadApi().uploadImage(requestBody)


            }catch (e: IOException){
                Log.d("APP INSERT Error", "${e.message} "+" ${e.localizedMessage}")
                return@launch
            }catch (e: HttpException){
                Log.d("HTTP INSERT Error", "${e.message}")
                return@launch
            }
        }

    }

    companion object {
        //static
        const val REQUEST_IMAGE_CAPTURE: Int = 1
        const val REQUEST_CODE_IMAGE: Int = 101
        const val REQUEST_IMAGE_PICK: Int= 2
    }

    override fun onProgressUpdate(porcentaje: Int) {
        pbProgress.progress = porcentaje
    }
}