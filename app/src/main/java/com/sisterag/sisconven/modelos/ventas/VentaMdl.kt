package com.sisterag.sisconven.modelos.ventas

import com.sisterag.sisconven.modelos.compras.DetCompraMdl
import java.sql.Date

/*ventas*/
data class VentaMdl(
    val id: Int?,
    val fecha: Date,
    val idcte: Int?,
    val activa: Int,
    val pagada: Int,
    val detvta: List<DetVentaMdl>
)
