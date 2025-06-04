package com.sisterag.sisconven.modelos.inventario

data class ArtResponse(
    val status: String?,
    val message: String?,
    val id: Int?,
    val idcat: Int,
    val articulo: String,
    val marca: String,
    val descripcion: String,
    val imagen: String=""
)
