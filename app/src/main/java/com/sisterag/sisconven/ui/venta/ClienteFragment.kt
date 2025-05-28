package com.sisterag.sisconven.ui.venta

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
import com.sisterag.sisconven.databinding.FragmentClienteBinding
import com.sisterag.sisconven.modelos.ventas.ClienteMdl
import com.sisterag.sisconven.servicios.ApiServiceCte
import com.sisterag.sisconven.utiles.EndPoint.comprueba
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

/**
 * A simple [Fragment] subclass.
 * Use the [ClienteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ClienteFragment : Fragment() {

    private lateinit var cteList: List<ClienteMdl>
    private var binding: FragmentClienteBinding? = null
    private lateinit var sNomb: String
    private lateinit var sTele: String
    private lateinit var sApod: String
    var lcFragment: Fragment? = null
    private lateinit var rvadapter: CteAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val _binding get() = binding!!

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {   // Inflate the layout for this fragment
        binding = FragmentClienteBinding.inflate(inflater, container, false)
        val root: View = binding!!.root
        binding!!.pbProgress.visibility = View.INVISIBLE

        val txtNomb: EditText = binding!!.etNombre
        txtNomb.setOnClickListener(View.OnClickListener {
            val rvwCte = binding!!.rvwRecyclr
            rvwCte.apply {
                rvwCte.setAdapter(null)
            }
        })
        val txtTele:EditText = binding!!.etTelefono
        txtTele.setOnClickListener(View.OnClickListener {
            val rvwCte = binding!!.rvwRecyclr
            rvwCte.apply {
                rvwCte.setAdapter(null)
            }
        })
        val txtApod: EditText = binding!!.etApodo
        txtApod.setOnClickListener(View.OnClickListener {
            val rvwCte = binding!!.rvwRecyclr
            rvwCte.apply {
                rvwCte.setAdapter(null)
            }
        })
        val btnEnvi: Button = binding!!.btnGuardar
        btnEnvi.setOnClickListener(View.OnClickListener {
            val bValor: Boolean = this.context?.let { it1 -> comprueba(binding!!.linearLayout, it1) } == true
            if(!bValor) {
                return@OnClickListener
            }
            binding!!.pbProgress.visibility = View.VISIBLE
            postRequest(this.context)
        })

        cteList = listOf()

        GlobalScope.launch(Dispatchers.IO) {
            val apiService = RetrofitClient.create(ApiServiceCte::class.java)
            val response = try {
                apiService.getCteAll()
            }catch (e: IOException){
                Log.d("APP Error", "${e.message} "+" ${e.localizedMessage}")
                return@launch
            }catch (e: HttpException){
                Log.d("HTTP Error", "${e.message}")
                return@launch
            }
            if(response.isSuccessful && response.body() != null) {
                withContext(Dispatchers.Main) {
                    cteList = response.body()!!
                    val rvwCte = binding!!.rvwRecyclr
                    rvwCte.apply {
                        rvadapter = CteAdapter(cteList)

                        rvwCte.setLayoutManager(LinearLayoutManager(activity))
                        rvwCte.setHasFixedSize(true)
                        adapter = rvadapter
                        rvwCte.setAdapter(adapter)
                    }
                }
            }
        }
        return root
    }

    private fun postRequest(context: Context?) {
        val apiService = RetrofitClient.create(ApiServiceCte::class.java)

            sNomb = binding!!.etNombre.text.toString()
            sTele = binding!!.etTelefono.text.toString()
            sApod = binding!!.etApodo.text.toString()
            binding!!.etNombre.text.clear()
            binding!!.etTelefono.text.clear()
            binding!!.etApodo.text.clear()

        lifecycleScope.launch {
            val response = apiService.CreateCte(sNomb, sTele, sApod)
            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    cteList = response.body()!!
                    val rvwCte = binding!!.rvwRecyclr
                    rvwCte.apply {
                        rvadapter = CteAdapter(cteList)
                        //rvadapter.setData(cateList)

                        val manager = LinearLayoutManager(this.context)
                        rvwCte.setLayoutManager(manager)
                        rvwCte.setHasFixedSize(true)
                        adapter = rvadapter
                        rvwCte.setAdapter(adapter)
                        binding!!.pbProgress.visibility = View.INVISIBLE
                    }
                }
            }else{
                Log.d("Error Retrofit", "${response.code()} - ${response.message()}")
            }
        }
    }

}