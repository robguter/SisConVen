package com.sisterag.sisconven.modelos.compras

/* detcompras */
data class DetCompraMdl(
    val id: Int?,
    val idcpra: Int?,
    val idart: String,
    val cantidad: Float,
    val costo: Float?,
    val costod: Float?
)
