package com.sisterag.sisconven.ui.venta

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sisterag.sisconven.R
import com.sisterag.sisconven.modelos.ventas.DetPedidoMdl
import com.sisterag.sisconven.utiles.EndPoint.formatNum

class DetPedViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val etId = view.findViewById<TextView>(R.id.tvwId)
    //val etIdPed = view.findViewById<TextView>(R.id.tvwIdped)
    val etIdart = view.findViewById<TextView>(R.id.tvwIdart)
    val etCant = view.findViewById<TextView>(R.id.tvwCantidad)
    val etCsto = view.findViewById<TextView>(R.id.tvwCosto)
    val etCstd = view.findViewById<TextView>(R.id.tvwCostod)

    fun render(detPedidoMdl: DetPedidoMdl) {
        etId.text = detPedidoMdl.id.toString()
        //etIdPed.text = detPedidoMdl.idPed.toString()
        etIdart.text = detPedidoMdl.idart.toString()

        val num = detPedidoMdl.cantidad
        etCant.text = num?.let { formatNum(it) }
        val num1 = detPedidoMdl.precio
        etCsto.text = num1?.let { formatNum(it) }
        val num2 = detPedidoMdl.preciod
        etCstd.text = num2?.let { formatNum(it) }
    }
}