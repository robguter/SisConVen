package com.sisterag.sisconven.modelos.ventas

data class DetPedidoMdl(
    val id: Int?=0,
    val idPed: Int?=0,
    val idart: Int?=0,
    val cantidad: Double? = 0.0,
    val precio: Double? = 0.0,
    val preciod: Double? = 0.0
)