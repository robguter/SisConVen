package com.sisterag.sisconven.modelos.inventario

data class ArticuloDto(
    val id: Int?,
    val idcat: Int,
    val articulo: String,
    val marca: String,
    val descripcion: String,
    val image: String=""
){
    fun name(): String {
        return "$articulo $marca $descripcion"
    }
}
