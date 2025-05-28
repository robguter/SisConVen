package com.sisterag.sisconven.ui.venta

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sisterag.sisconven.databinding.ItemPedDetListBinding
import com.sisterag.sisconven.databinding.ItemPeddetBinding
import com.sisterag.sisconven.modelos.ventas.DetPedidoMdl

class PedAdapter(private val detPedList:List<DetPedidoMdl>): RecyclerView.Adapter<PedAdapter.ViewHolder>() {

    private var myList = emptyList<DetPedidoMdl>()

    inner class ViewHolder(val binding: ItemPeddetBinding)
        : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemPeddetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return detPedList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = detPedList[position]
        holder.binding.apply {
            //tvId.text = "${currentItem.id}"
            //tvIdped.text = "${currentItem.idPed}"
            tvIdart.text = currentItem.idart.toString()
            tvCantidad.text = currentItem.cantidad.toString()
            tvCosto.text = currentItem.precio.toString()
            tvCostod.text = currentItem.preciod.toString()
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    public fun setData(newList: List<DetPedidoMdl>){
        myList = newList
        notifyDataSetChanged()
    }
}