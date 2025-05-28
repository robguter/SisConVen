package com.sisterag.sisconven.modelos.compras

data class ProveeMdl(
    var id: Int?,
    val nombre: String) {
        fun name(): String {
            return "$id $nombre"
        }
    }
