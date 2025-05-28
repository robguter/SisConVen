package com.sisterag.sisconven.ui.venta

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sisterag.sisconven.databinding.ItemClteBinding
import com.sisterag.sisconven.modelos.ventas.ClienteMdl



class CteAdapter(private val cteList:List<ClienteMdl>): RecyclerView.Adapter<CteAdapter.ViewHolder>() {

    private var myList = emptyList<ClienteMdl>()

    inner class ViewHolder(val binding: ItemClteBinding)
        :RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemClteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return cteList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = cteList[position]
        holder.binding.apply {
            tvwId.text = "${currentItem.id}"
            tvwNombre.text = currentItem.nombre
            tvwTelefono.text = currentItem.telefono
            tvwApodo.text = currentItem.apodo
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    public fun setData(newList: List<ClienteMdl>){
        myList = newList
        notifyDataSetChanged()
    }
}