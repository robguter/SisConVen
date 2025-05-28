package com.sisterag.sisconven.ui.compra

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageSwitcher
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import com.sisterag.sisconven.R.layout
import com.sisterag.sisconven.clients.RetrofitClient
import com.sisterag.sisconven.databinding.FragmentCompraBinding
import com.sisterag.sisconven.modelos.compras.CompraMdl
import com.sisterag.sisconven.modelos.compras.ProveeMdl
import com.sisterag.sisconven.servicios.ApiServiceCpra
import com.sisterag.sisconven.servicios.ApiServiceProv
import com.sisterag.sisconven.utiles.EndPoint.BuscaEspacio
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException


class CompraFragment : Fragment() {

    val apiService = RetrofitClient.create(ApiServiceCpra::class.java)
    val apiServicep = RetrofitClient.create(ApiServiceProv::class.java)
    private var binding: FragmentCompraBinding? = null

    private lateinit var provList: List<ProveeMdl>
    private lateinit var cpraList: CompraMdl
    private lateinit var provLsts: List<String>

    private lateinit var imsBaja: ImageSwitcher
    private lateinit var spnIdprov:Spinner
    private lateinit var sIdprov:String
    private lateinit var etTele: EditText
    private lateinit var sTele: String
    private lateinit var etCedrif: EditText
    private lateinit var sCedrif: String
    private lateinit var swtPagada: SwitchCompat
    private lateinit var sPaga: String
    private lateinit var swtEstatus: SwitchCompat
    private lateinit var sEstat: String
    private  lateinit var ethIdCpra: EditText
    private  lateinit var ethIdProv: EditText
    var lcFragment: Fragment? = null
    private lateinit var rvadapter: ProvAdapter
    public lateinit var listAdapter:ArrayAdapter<String>
    private var bandera = false

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCompraBinding.inflate(inflater, container, false)
        val root: View = binding!!.root

        spnIdprov = binding!!.spnIdprov
        ethIdCpra = binding!!.ethIdCpra
        ethIdProv = binding!!.ethIdProv

        spnIdprov.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                if (!bandera) {
                    bandera = true
                    return;
                }
                val item = parent!!.getItemAtPosition(position).toString()
                //item = "5$item"
                buscaCpraByProv(item)
            }

            private fun buscaCpraByProv(item: String) {
                //cpraList = listOf()
                provLsts = mutableListOf<String>()

                val cVal = BuscaEspacio(item)
                ethIdProv.setText(cVal)

                GlobalScope.launch(Dispatchers.IO) {
                    val response = try {
                        apiService.getCpraByIdProv(cVal)
                    }catch (e: IOException){
                        Log.d("APP Error", "${e.stackTraceToString()}")
                        return@launch
                    }catch (e: HttpException){
                        Log.d("HTTP Error", "${e.message}")
                        return@launch
                    }
                    if(response.isSuccessful && response.body() != null) {
                        withContext(Dispatchers.Main) {
                            cpraList = response.body()!!
                            Toast.makeText(context, "RESPUESTAA: $cpraList.data $cpraList.exito $cpraList.mensaje", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) { }
        }
        imsBaja = binding!!.imsBajar
        imsBaja.setOnClickListener {
            etTele = binding!!.etTelefono
            sTele = etTele.text.toString()

            etCedrif = binding!!.etCedrif
            sCedrif = etCedrif.text.toString()

            swtPagada = binding!!.swtPagada
            sPaga = swtPagada.text.toString()

            swtEstatus = binding!!.swtEstatus
            sEstat = swtEstatus.text.toString()
        }

        this.initializeUI()
        return root
    }
    @OptIn(DelicateCoroutinesApi::class)
    private fun initializeUI() {

        provList = listOf()
        provLsts = mutableListOf<String>()

        GlobalScope.launch(Dispatchers.IO) {
            val response = try {
                apiServicep.getProvAllCbo()
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
                    var n = 0
                    for (emp in provList) {
                        if(n == 0) {
                            (provLsts as MutableList<String>).add("Seleccione")
                            n++
                        }
                        (provLsts as MutableList<String>).add(emp.name())
                    }

                    val context: Context? = activity

                    val adapter =
                        context?.let {
                            ArrayAdapter<String>(
                                it.applicationContext,
                                layout.spinner_item,
                                provLsts
                            )
                        }//ThemeOverlay.MaterialComponents.Dark.ActionBar
                    adapter?.setDropDownViewResource(layout.spinner_dropdown_item)
                    spnIdprov.adapter = adapter
                    spnIdprov.setAdapter(adapter)
                    spnIdprov.onItemSelectedListener
                }
            }
        }
    }
}