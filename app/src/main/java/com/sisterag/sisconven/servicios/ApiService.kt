package com.sisterag.sisconven.servicios

import com.sisterag.sisconven.modelos.inventario.ArtResponse
import com.sisterag.sisconven.utiles.EndPoint.AddArtUrl
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @Multipart
    @POST(AddArtUrl)
    suspend fun uploadImage(
        @Part("idcat") idcat: RequestBody,
        @Part("articulo") articulo: RequestBody,
        @Part("marca") marca: RequestBody,
        @Part("descripcion") descripcion: RequestBody,
        @Part imagen: MultipartBody.Part
    ): Response<ArtResponse>
}