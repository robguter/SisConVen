package com.sisterag.sisconven.servicios

import com.sisterag.sisconven.modelos.inventario.CateMdl
import com.sisterag.sisconven.modelos.ventas.ClienteMdl
import com.sisterag.sisconven.utiles.EndPoint.ActCateUrl
import com.sisterag.sisconven.utiles.EndPoint.ActCteUrl
import com.sisterag.sisconven.utiles.EndPoint.AddCateUrl
import com.sisterag.sisconven.utiles.EndPoint.DelCateUrl
import com.sisterag.sisconven.utiles.EndPoint.DelCteUrl
import com.sisterag.sisconven.utiles.EndPoint.GetAllCateUrl
import com.sisterag.sisconven.utiles.EndPoint.GetCateXidUrl
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiServiceCate {
    @GET(GetAllCateUrl)
    suspend fun getCateAll(): Response<List<CateMdl>>

    @GET(GetCateXidUrl)
    suspend fun getCateById(
        @Path("id") id: String
    ): Response<CateMdl>

    @FormUrlEncoded
    @POST(AddCateUrl)
    suspend fun CreateCate(
        @Field("categoria") categoria: String,
        @Field("descripcion") descripcion: String
    ): Response<List<CateMdl>>

    @FormUrlEncoded
    @PUT(ActCateUrl)
    suspend fun UpdateCte(
        @Field("id") id: Int,
        @Field("categoria") categoria: String,
        @Field("descripcion") descripcion: String
    ): Response<List<CateMdl>>

    @FormUrlEncoded
    @DELETE(DelCateUrl)
    suspend fun DeleteCte(
        @Field("id") id: Int
    ): Response<List<CateMdl>>
}