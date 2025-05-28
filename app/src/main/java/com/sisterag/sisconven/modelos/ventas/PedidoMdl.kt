package com.sisterag.sisconven.modelos.ventas

import com.google.gson.annotations.SerializedName
import com.sisterag.sisconven.modelos.compras.DetCompraMdl
import java.sql.Date

data class PedidoMdl(
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("fecha")
    val fecha: Date? = null,
    @SerializedName("idcte")
    val idcte: Int? = 0,
    @SerializedName("estatus")
    val estatus: Int? = 0,
    @SerializedName("detcpra")
    val detcpra: List<DetPedidoMdl>? = null
)
