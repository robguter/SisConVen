package com.sisterag.sisconven.ui.compra

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sisterag.sisconven.databinding.ItemProvBinding
import com.sisterag.sisconven.modelos.compras.ProveedorMdl

class ProvAdapter(private val provList: List<ProveedorMdl>):RecyclerView.Adapter<ProvAdapter.ViewHolder>() {

    private var myList = emptyList<ProveedorMdl>()

    class ViewHolder(val binding: ItemProvBinding)
        :RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemProvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return provList.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = provList[position]
        holder.binding.apply {
            tvwId.text = "${currentItem.id}"
            tvwCedrif.text = currentItem.cedrif
            tvwNombre.text = currentItem.nombre
            tvwTelefono.text = currentItem.telefono
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public fun setData(newList: List<ProveedorMdl>){
        myList = newList
        notifyDataSetChanged()
    }
}