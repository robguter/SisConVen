package com.sisterag.sisconven.modelos.varios

import java.sql.Date

data class Dolar(
    val id: Int?,
    val fecha: Date,
    val bcv: Float,
    val promedio: Float?,
    val paralelo: Float
)
