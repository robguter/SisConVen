package com.sisterag.sisconven.ui.inventario

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sisterag.sisconven.databinding.ItemCateBinding
import com.sisterag.sisconven.modelos.inventario.CateMdl

class CateAdapter(private val cateList:List<CateMdl>): RecyclerView.Adapter<CateAdapter.ViewHolder>() {

    private var myList = emptyList<CateMdl>()

    inner class ViewHolder(val binding: ItemCateBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            ItemCateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return cateList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = cateList[position]
        holder.binding.apply {
            tvwIdcat.text = "${currentItem.id}"
            tvwCateg.text = currentItem.categoria
            tvwDescr.text = currentItem.descripcion

        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public fun setData(newList: List<CateMdl>){
        myList = newList
        notifyDataSetChanged()
    }
}