package com.sisterag.sisconven.modelos.ventas

data class ClienteMdl(
    val id: Int?,
    val nombre: String,
    val telefono: String,
    val apodo: String
) {
    fun name(): String {
        return "$id $nombre"
    }
}