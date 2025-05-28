package com.sisterag.sisconven.ui.inventario
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.sisterag.sisconven.databinding.ItemArtBinding
import com.sisterag.sisconven.modelos.inventario.ArticuloDto
import com.sisterag.sisconven.utiles.EndPoint.IMG_URL
class ArtAdapter(private val artList:List<ArticuloDto>): RecyclerView.Adapter<ArtAdapter.ViewHolder>() {

    private var myList = emptyList<ArticuloDto>()
    private lateinit var context: Context
    inner class ViewHolder(val binding: ItemArtBinding)
        :RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            ItemArtBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }
    override fun getItemCount(): Int {
        return artList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currItem = artList[position]
        if(currItem.id!!.toInt() > 0 && currItem.id > 0) {
            holder.binding.apply {
                tvwId.text = "${currItem.id}"
                tvwIdcat.text = "${currItem.idcat}"
                tvwArticulo.text = currItem.articulo
                tvwMarca.text = currItem.marca
                tvwDescrip.text = currItem.descripcion

                Glide.with(context)
                    .load(IMG_URL + currItem.image)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(imgImag)
            }
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    public fun setData(newList: List<ArticuloDto>){
        myList = newList
        notifyDataSetChanged()
    }
}