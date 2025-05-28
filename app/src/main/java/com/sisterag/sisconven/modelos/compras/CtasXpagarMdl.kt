package com.sisterag.sisconven.modelos.compras

import java.sql.Date

/*ctasxpagar*/
data class CtasXpagarMdl(
    val id: Int?,
    val fecha: Date,
    val idcpra: Int?,
    val monto: Float?,
    val montod: Float?,
    val estatus: String
)
