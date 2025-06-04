package com.sisterag.sisconven.ui.inventario

import android.content.Context
import android.net.Uri
import android.os.Bundle

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

import androidx.constraintlayout.widget.ConstraintLayout

import androidx.fragment.app.Fragment

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sisterag.sisconven.clients.RetrofitClient
import com.sisterag.sisconven.clients.RetrofitClientIA
import com.sisterag.sisconven.databinding.FragmentArticuloBinding
import com.sisterag.sisconven.modelos.inventario.ArticuloDto
import com.sisterag.sisconven.servicios.ApiServiceArt

import com.sisterag.sisconven.utiles.EndPoint.comprueba
import com.sisterag.sisconven.utiles.MessageOwn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File
import java.io.FileOutputStream

import java.io.IOException

/**
 * A simple [Fragment] subclass.
 * Use the [ArticuloFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ArticuloFragment : Fragment() {

    private val apiService = RetrofitClient.create(ApiServiceArt::class.java)
    private lateinit var artList: List<ArticuloDto>
    private var binding: FragmentArticuloBinding? = null
    private lateinit var rvwArt: RecyclerView

    private lateinit var txtArt: EditText
    private lateinit var txtIdc: EditText
    private lateinit var txtDesc: EditText
    private lateinit var txtMarc: EditText
    private lateinit var btnUpload: Button
    private lateinit var image: File

    private lateinit var sIdct: String
    private lateinit var sArti: String
    private lateinit var sMarc: String
    private lateinit var sDesc: String

    private lateinit var rvadapter: ArtAdapter
    private lateinit var ivImage: ImageView
    private lateinit var pbProgress: ProgressBar
    private lateinit var lLayout: ConstraintLayout

    private lateinit var context: Context

    private val contentResolver = activity?.contentResolver
    private lateinit var imageUri: Uri

    private lateinit var getContent: ActivityResultLauncher<String> // Launcher para seleccionar contenido

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

        btnUpload = binding!!.btnUpload
        btnUpload.setOnClickListener(View.OnClickListener {
        })

        ivImage.setOnClickListener(View.OnClickListener {
            getContent.launch("image/*")
        })
        getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                imageUri = it
                ivImage.setImageURI(it)
            }
        }
        val btnEnvi: Button = binding!!.btnGuardar
        btnEnvi.setOnClickListener(View.OnClickListener {
            val bValor: Boolean = comprueba(lLayout, this.context)
            if(!bValor) {
                return@OnClickListener
            }
            binding!!.pbProgress.visibility = View.VISIBLE
            sIdct = txtIdc.text.toString()
            sArti = txtArt.text.toString()
            sMarc = txtMarc.text.toString()
            sDesc = txtDesc.text.toString()
            imageUri.let { uri ->
                // Si hay una imagen seleccionada, procede a subirla
                enviaPost(sIdct, sArti, sMarc, sDesc, uri)
            } ?: run {
                MessageOwn(context, "Por favor, selecciona una imagen primero.")
            }
        })

        artList = listOf()

        GlobalScope.launch(Dispatchers.IO) {
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

    private fun enviaPost(sIdcat: String, sArti: String, sMarc: String, sDesc: String, imageUri: Uri) {
        var file: File? = null
        CoroutineScope(Dispatchers.IO).launch {
            try {
                file = File(context.cacheDir, "temp_image.jpg")
                context.contentResolver!!.openInputStream(imageUri)?.use { input ->
                    FileOutputStream(file).use { output ->
                        input.copyTo(output)
                    }
                }
                Log.d("UPLOAD", "Tamaño del archivo temporal: ${file?.length()} bytes") // Verificar tamaño

                val mimeType = contentResolver?.getType(imageUri) ?: "image/*"
                val requestFile = file!!.asRequestBody(mimeType.toMediaTypeOrNull())
                val imagePart = MultipartBody.Part.createFormData("imagen", file!!.name, requestFile)
                val tPart1 = sIdcat.toRequestBody("text/plain".toMediaTypeOrNull())
                val tPart2 = sArti.toRequestBody("text/plain".toMediaTypeOrNull())
                val tPart3 = sMarc.toRequestBody("text/plain".toMediaTypeOrNull())
                val tPart4 = sDesc.toRequestBody("text/plain".toMediaTypeOrNull())
                val response = RetrofitClientIA.apiService.uploadImage(tPart1, tPart2, tPart3, tPart4, imagePart)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        MessageOwn(context, "¡Subida exitosa!")
                        println("Respuesta del servidor: ${response.body()?.toString()}")
                    } else {
                        val errorBody = response.errorBody()?.string()
                        Log.e("UPLOAD_ERROR_BODY", "${response.code()} $errorBody")
                        MessageOwn(
                            context,
                            "Error en la subida: ${response.code()} - $errorBody")
                        println("Error de subida: ${response.code()} - ${response.message()} - $errorBody")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    MessageOwn(context, "Error inesperado: ${e.localizedMessage} - imageUri - "+imageUri.toString())
                    Log.i("EXCEPTION","Error inesperado: ${e.localizedMessage} - imageUri - "+imageUri.toString()+" - "+e.message)
                    e.printStackTrace()
                }
            } finally {
                file?.delete()
            }
        }
    }

    companion object {        //static
        const val REQUEST_IMAGE_CAPTURE: Int = 1
        const val REQUEST_CODE_IMAGE: Int = 101
        const val REQUEST_IMAGE_PICK: Int= 2
    }

    fun onProgressUpdate(porcentaje: Int) {
        pbProgress.progress = porcentaje
    }
}