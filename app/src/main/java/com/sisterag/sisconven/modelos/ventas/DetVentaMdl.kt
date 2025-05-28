package com.sisterag.sisconven.modelos.ventas

/*detventas*/
data class DetVentaMdl(
    val id: Int?,
    val idvta: Int?,
    val idart: String,
    val cantidad: Float,
    val precio: Float,
    val preciod: Float?
)
