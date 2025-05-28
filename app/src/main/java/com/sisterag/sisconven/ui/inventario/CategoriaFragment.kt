package com.sisterag.sisconven.ui.inventario

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.sisterag.sisconven.clients.RetrofitClient
import com.sisterag.sisconven.databinding.FragmentCategoriaBinding
import com.sisterag.sisconven.modelos.inventario.CateMdl
import com.sisterag.sisconven.servicios.ApiServiceCate
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

/**
 * A simple [Fragment] subclass.
 * Use the [CategoriaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CategoriaFragment : Fragment() {

    val apiService = RetrofitClient.create(ApiServiceCate::class.java)
    private lateinit var cateList: List<CateMdl>
    private var binding: FragmentCategoriaBinding? = null
    private lateinit var sCate: String
    private lateinit var sDesc: String

    var lcFragment: Fragment? = null
    private lateinit var rvadapter: CateAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val _binding get() = binding!!

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCategoriaBinding.inflate(inflater, container, false)
        val root: View = binding!!.root

        //binding!!.pbProgress.visibility = View.INVISIBLE

        val btnEnvi: Button = binding!!.btnGuardar
        btnEnvi.setOnClickListener(View.OnClickListener {
            binding!!.pbProgress.visibility = View.VISIBLE
            postRequest(this.context)
        })

        val txtCate: EditText = binding!!.etCategoria
        txtCate.setOnClickListener(View.OnClickListener {
            val rvwCate = binding!!.rvwCate
            rvwCate.apply {
                rvwCate.setAdapter(null)
            }
        })

        val txtDesc: EditText = binding!!.etDescrip
        txtDesc.setOnClickListener(View.OnClickListener {
            val rvwCate = binding!!.rvwCate
            rvwCate.apply {
                rvwCate.setAdapter(null)
            }
        })

        //binding!!.pbProgress.visibility = View.VISIBLE

        cateList = listOf()

        GlobalScope.launch(Dispatchers.IO) {
            val response = try {
                apiService.getCateAll()
            }catch (e: IOException){
                Log.d("APP Error", "${e.message} "+" ${e.localizedMessage}")
                return@launch
            }catch (e: HttpException){
                Log.d("HTTP Error", "${e.message}")
                return@launch
            }
            if(response.isSuccessful && response.body() != null) {
                withContext(Dispatchers.Main) {
                    cateList = response.body()!!
                    val rvwCate = binding!!.rvwCate
                    rvwCate.apply {
                        rvadapter = CateAdapter(cateList)

                        rvwCate.setLayoutManager(LinearLayoutManager(activity))
                        rvwCate.setHasFixedSize(true)
                        adapter = rvadapter
                        rvwCate.setAdapter(adapter)  //binding!!.pbProgress.visibility = View.INVISIBLE
                    }
                }
            }
        }
        return root
    }
    private fun postRequest(context: Context?) {
        val service:ApiServiceCate
        try {
            sCate = binding!!.etCategoria.text.toString()
            sDesc = binding!!.etDescrip.text.toString()
            binding!!.etCategoria.text.clear()
            binding!!.etDescrip.text.clear()

        }catch (e: IOException){
            Log.d("APP Error", "${e.message} + ${e.localizedMessage}")
            binding!!.pbProgress.visibility = View.INVISIBLE
            return
        }catch (e: HttpException){
            Log.d("HTTP Error", "${e.message} + ${e.code()}")
            binding!!.pbProgress.visibility = View.INVISIBLE
            return
        }
        lifecycleScope.launch {
            /*** val objRequest = CateMdlRequest( categoria = sCate, descripcion = sDesc ) ***/
            val response = apiService.CreateCate(sCate, sDesc)
            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    cateList = response.body()!!
                    val rvwCate = binding!!.rvwCate
                    rvwCate.apply {
                        rvadapter = CateAdapter(cateList)
                        //rvadapter.setData(cateList)

                        val manager = LinearLayoutManager(this.context)
                        rvwCate.setLayoutManager(manager)
                        rvwCate.setHasFixedSize(true)
                        adapter = rvadapter
                        rvwCate.setAdapter(adapter)
                        //binding!!.pbProgress.visibility = View.INVISIBLE
                    }
                }
            }else{
                Log.d("Error Retrofit", "${response.code()} - ${response.message()}")
            }
        }
    }

}