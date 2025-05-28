package com.sisterag.sisconven.ui.compra

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
import com.sisterag.sisconven.databinding.FragmentProveeBinding
import com.sisterag.sisconven.modelos.compras.ProveedorMdl
import com.sisterag.sisconven.servicios.ApiServiceProv
import com.sisterag.sisconven.utiles.EndPoint.BASE_URL
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
 * Use the [ProveeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProveeFragment : Fragment() {

    val apiService = RetrofitClient.create(ApiServiceProv::class.java)
    private lateinit var provList: List<ProveedorMdl>
    private var binding: FragmentProveeBinding? = null
    private lateinit var sCdrf: String
    private lateinit var sNomb: String
    private lateinit var sTele: String
    var lcFragment: Fragment? = null
    private lateinit var rvadapter: ProvAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val _binding get() = binding!!

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {   // Inflate the layout for this fragment
        binding = FragmentProveeBinding.inflate(inflater, container, false)
        val root: View = binding!!.root
        binding!!.pbProgress.visibility = View.INVISIBLE

        val txtNomb: EditText = binding!!.etNombre
        txtNomb.setOnClickListener(View.OnClickListener {
            val rvwCte = binding!!.rvwRecyclrProv
            rvwCte.apply {
                rvwCte.setAdapter(null)
            }
        })
        val txtTele: EditText = binding!!.etTelefono
        txtTele.setOnClickListener(View.OnClickListener {
            val rvwProv = binding!!.rvwRecyclrProv
            rvwProv.apply {
                rvwProv.setAdapter(null)
            }
        })
        val txtApod: EditText = binding!!.etCedrif
        txtApod.setOnClickListener(View.OnClickListener {
            val rvwProv = binding!!.rvwRecyclrProv
            rvwProv.apply {
                rvwProv.setAdapter(null)
            }
        })
        val btnEnvi: Button = binding!!.btnGuardar
        btnEnvi.setOnClickListener(View.OnClickListener {
            binding!!.pbProgress.visibility = View.VISIBLE
            postRequest(this.context)
        })
        provList = listOf()

        GlobalScope.launch(Dispatchers.IO) {
            val response = try {
                apiService.getProvAll()
            }catch (e: IOException){
                Log.d("APP Error", "${e.message} "+" ${e.localizedMessage}")
                return@launch
            }catch (e: HttpException){
                Log.d("HTTP Error", "${e.message}")
                return@launch
            }
            if(response.isSuccessful && response.body() != null) {
                withContext(Dispatchers.Main) {
                    provList = response.body()!!
                    val rvwProv = binding!!.rvwRecyclrProv
                    rvwProv.apply {
                        rvadapter = ProvAdapter(provList)

                        rvwProv.setLayoutManager(LinearLayoutManager(activity))
                        rvwProv.setHasFixedSize(true)
                        adapter = rvadapter
                        rvwProv.setAdapter(adapter)
                    }
                }
            }
        }
        return root
    }
    private fun postRequest(context: Context?) {
        val service: ApiServiceProv
        try {
            sNomb = binding!!.etNombre.text.toString()
            sTele = binding!!.etTelefono.text.toString()
            sCdrf = binding!!.etCedrif.text.toString()
            binding!!.etNombre.text.clear()
            binding!!.etTelefono.text.clear()
            binding!!.etCedrif.text.clear()
            val retrofit =
                Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            service = retrofit.create(ApiServiceProv::class.java)
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
            val response = service.CreateProv(sCdrf, sNomb, sTele)
            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    provList = response.body()!!
                    val rvwProv = binding!!.rvwRecyclrProv
                    rvwProv.apply {
                        rvadapter = ProvAdapter(provList)
                        //rvadapter.setData(cateList)

                        val manager = LinearLayoutManager(this.context)
                        rvwProv.setLayoutManager(manager)
                        rvwProv.setHasFixedSize(true)
                        adapter = rvadapter
                        rvwProv.setAdapter(adapter)
                        binding!!.pbProgress.visibility = View.INVISIBLE
                    }
                }
            }else{
                Log.d("Error Retrofit", "${response.code()} - ${response.message()}")
            }
        }
    }
}