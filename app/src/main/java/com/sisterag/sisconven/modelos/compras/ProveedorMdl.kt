package com.sisterag.sisconven.modelos.compras

val sConca: String = ""

/*proveedores*/
data class ProveedorMdl(
    var id: Int?,
    val cedrif: String,
    val nombre: String,
    val telefono: String,
    val email: String?,
    val direccion: String?,
    val codpostal: String?,
    val ciudad: String?,
    val estado: String?,
    val nombrecontacto: String?,
    val telcontacto: String?,
    val notas: String?
)