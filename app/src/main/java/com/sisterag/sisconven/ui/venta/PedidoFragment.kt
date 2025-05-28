package com.sisterag.sisconven.ui.venta

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.sisterag.sisconven.R
import com.sisterag.sisconven.clients.RetrofitClient
import com.sisterag.sisconven.databinding.FragmentPedidoBinding
import com.sisterag.sisconven.modelos.ventas.ClienteMdl
import com.sisterag.sisconven.modelos.ventas.DetPedidoMdl
import com.sisterag.sisconven.modelos.ventas.PedidoMdl
import com.sisterag.sisconven.servicios.ApiServiceCte
import com.sisterag.sisconven.servicios.ApiServicePed
import com.sisterag.sisconven.utiles.EndPoint.BuscaEspacio
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException


/**
 * A simple [Fragment] subclass.
 * Use the [PedidoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PedidoFragment : Fragment() {

    val apiService = RetrofitClient.create(ApiServiceCte::class.java)
    val apiServicep = RetrofitClient.create(ApiServicePed::class.java)
    private val dtPdList : ArrayList<DetPedidoMdl> = ArrayList()
    private lateinit var detpedAdapter : DetPedAdapter
    lateinit var binding : FragmentPedidoBinding

    private lateinit var cteList: List<ClienteMdl>
    private lateinit var dtPedList: List<DetPedidoMdl>
    private lateinit var detPed: DetPedidoMdl
    private lateinit var pedList: PedidoMdl
    private lateinit var cteLsts: List<String>

    private lateinit var imsBaja: ImageSwitcher
    private lateinit var spnIdcte: Spinner
    private lateinit var sIdcte:String
    private lateinit var etTele: EditText
    private lateinit var sTele: String
    private lateinit var etCedrif: EditText
    private lateinit var sCedrif: String
    private lateinit var sPaga: String
    private lateinit var swtEstatus: SwitchCompat
    private lateinit var sEstat: String
    private  lateinit var ethIdPed: EditText
    private  lateinit var ethIdCte: EditText

    private lateinit var edtId: EditText
    private lateinit var edtIdped: EditText
    private lateinit var edtIdart: EditText
    private lateinit var edtCantidad: EditText
    private lateinit var edtCosto: EditText
    private lateinit var edtCostod: EditText

    private lateinit var sNomb: String
    private lateinit var sApod: String

    var lcFragment: Fragment? = null
    private lateinit var rvadapter: DetPedAdapter

    public lateinit var listAdapter: ArrayAdapter<String>
    private var bandera = false

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPedidoBinding.inflate(inflater, container, false)
        val root: View = binding!!.root
        binding!!.pbProgress.visibility = View.INVISIBLE

        spnIdcte = binding!!.spnIdcte
        ethIdPed = binding!!.ethIdPed
        ethIdCte = binding!!.ethIdCte

        spnIdcte.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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
                buscaPedByCte(item)
            }

            private fun buscaPedByCte(item: String) {
                //pedList = listOf()
                cteLsts = mutableListOf<String>()

                val cVal = BuscaEspacio(item)
                ethIdCte.setText(cVal)

                GlobalScope.launch(Dispatchers.IO) {
                    val response = try {
                        apiServicep.getPedByIdCte(cVal)
                    }catch (e: IOException){
                        Log.d("APP Error", e.stackTraceToString())
                        return@launch
                    }catch (e: HttpException){
                        Log.d("HTTP Error", "${e.message}")
                        return@launch
                    }
                    if(response.isSuccessful && response.body() != null) {
                        withContext(Dispatchers.Main) {
                            pedList = response.body()!!
                            Toast.makeText(context, "RESPUESTAA: $pedList.data $pedList.exito $pedList.mensaje", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) { }
        }
        /*edtId = binding.edtId
        edtIdped = binding.edtIdped
        edtIdart = binding.edtIdart*/
        edtCantidad = binding.edtCantidad
        edtCosto = binding.edtCosto
        edtCostod = binding.edtCostod

        imsBaja = binding!!.imsBajar
        imsBaja.setOnClickListener {
            etTele = binding!!.etTelefono
            sTele = etTele.text.toString()

            //sCedrif = etCedrif.text.toString()

            swtEstatus = binding!!.swtEstatus
            sEstat = swtEstatus.text.toString()

            val iId = edtId.text.toString()
            val iIdped = edtIdped.text.toString()
            val iIdart = edtIdart.text.toString()
            val dCant = edtCantidad.text.toString()
            val dCosto = edtCosto.text.toString()
            val dCostd = edtCostod.text.toString()

            edtId.setText("")
            edtIdped.setText("")
            edtIdart.setText("")
            edtCantidad.setText("")
            edtCosto.setText("")
            edtCostod.setText("")

            detPed = DetPedidoMdl(iId.toInt(), iIdped.toInt(), iIdart.toInt(), dCant.toDouble(), dCosto.toDouble(), dCostd.toDouble())
            dtPdList.add(detPed)
            initRecycler()
        }
        setAdapter()

        initializeUI()
        return root
    }
    private fun setAdapter(){
        detpedAdapter = DetPedAdapter(dtPdList)
        binding.rvwRecyclrPed.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = detpedAdapter
        }
    }
    private fun initRecycler(){
        val recyclreView = binding.rvwRecyclrPed
        recyclreView.layoutManager = LinearLayoutManager(this.context)
        recyclreView.adapter = DetPedAdapter(dtPdList)
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun initializeUI() {
        cteList = listOf()
        cteLsts = mutableListOf<String>()

        GlobalScope.launch(Dispatchers.IO) {
            val response = try {
                apiService.getCteAllCbo()
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
                    var n = 0
                    for (emp in cteList) {
                        if(n == 0) {
                            (cteLsts as MutableList<String>).add("Seleccione")
                            n++
                        }
                        (cteLsts as MutableList<String>).add(emp.name())
                    }

                    val context: Context? = activity

                    val adapter =
                        context?.let {
                            ArrayAdapter<String>(
                                it.applicationContext,
                                R.layout.spinner_item,
                                cteLsts
                            )
                        }//ThemeOverlay.MaterialComponents.Dark.ActionBar
                    adapter?.setDropDownViewResource(R.layout.spinner_dropdown_item)
                    spnIdcte.adapter = adapter
                    spnIdcte.setAdapter(adapter)
                    spnIdcte.onItemSelectedListener
                }
            }
        }
    }
}