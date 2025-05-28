package com.sisterag.sisconven.servicios

import com.sisterag.sisconven.modelos.ventas.PedidoMdl
import com.sisterag.sisconven.utiles.EndPoint.ActPedUrl
import com.sisterag.sisconven.utiles.EndPoint.AddPedUrl
import com.sisterag.sisconven.utiles.EndPoint.DelPedUrl
import com.sisterag.sisconven.utiles.EndPoint.GetPedXidUrl
import com.sisterag.sisconven.utiles.EndPoint.GetAllPedUrl
import com.sisterag.sisconven.utiles.EndPoint.GetPedXidCteUrl

import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiServicePed {
    @GET(GetAllPedUrl)
    suspend fun getPedAll(): Response<List<PedidoMdl>>

    @GET(GetPedXidUrl)
    suspend fun getPedById(
        @Path("id") id: String
    ): Response<PedidoMdl>

    @GET(GetPedXidCteUrl)
    suspend fun getPedByIdCte(
        @Path("idprov") idprov: String
    ): Response<PedidoMdl>

    @FormUrlEncoded
    @POST(AddPedUrl)
    suspend fun CreatePed(
        @Field("nombre") nombre: String,
        @Field("telefono") telefono: String,
        @Field("apodo") apodo: String
    ): Response<List<PedidoMdl>>

    @FormUrlEncoded
    @PUT(ActPedUrl)
    suspend fun UpdatePed(
        @Field("id") id: Int,
        @Field("nombre") nombre: String,
        @Field("telefono") telefono: String,
        @Field("apodo") apodo: String
    ): Response<List<PedidoMdl>>

    @FormUrlEncoded
    @DELETE(DelPedUrl)
    suspend fun DeletePed(
        @Field("id") id: Int
    ): Response<List<PedidoMdl>>
}