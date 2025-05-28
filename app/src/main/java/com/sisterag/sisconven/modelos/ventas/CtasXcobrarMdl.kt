package com.sisterag.sisconven.modelos.ventas

import java.sql.Date

/*ctasxcobrar*/
data class CtasXcobrarMdl(
    val id: Int?,
    val fecha: Date,
    val idvta: Int?,
    val monto: Float?,
    val montod: Float?,
    val estatus: String
)
