package com.sisterag.sisconven.modelos.inventario

data class UploadResponse (
    val error: Boolean,
    val message: String,
    val image: String?
)