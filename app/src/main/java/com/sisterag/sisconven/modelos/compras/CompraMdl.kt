package com.sisterag.sisconven.modelos.compras

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.sql.Date
/* TABLE compras */
data class CompraMdl(
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("fecha")
    val fecha: Date? = null,
    @SerializedName("idprov")
    val idprov: Int? = 0,
    @SerializedName("activa")
    val activa: Int? = 0,
    @SerializedName("pagada")
    val pagada: Int? = 0,
    @SerializedName("detcpra")
    val detcpra: List<DetCompraMdl>? = null
)