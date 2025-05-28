package com.sisterag.sisconven.servicios

import com.sisterag.sisconven.modelos.compras.ProveeMdl
import com.sisterag.sisconven.modelos.ventas.ClienteMdl
import com.sisterag.sisconven.utiles.EndPoint.ActCteUrl
import com.sisterag.sisconven.utiles.EndPoint.AddCteUrl
import com.sisterag.sisconven.utiles.EndPoint.DelCteUrl
import com.sisterag.sisconven.utiles.EndPoint.GetAllCteUrl
import com.sisterag.sisconven.utiles.EndPoint.GetAllProvUrl
import com.sisterag.sisconven.utiles.EndPoint.GetCteXidUrl
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiServiceCte {
    @GET(GetAllCteUrl)
    suspend fun getCteAll(): Response<List<ClienteMdl>>

    @GET(GetAllCteUrl)
    suspend fun getCteAllCbo(): Response<List<ClienteMdl>>

    @GET(GetCteXidUrl)
    suspend fun getCteById(
        @Path("id") id: String
    ): Response<ClienteMdl>

    @FormUrlEncoded
    @POST(AddCteUrl)
    suspend fun CreateCte(
        @Field("nombre") nombre: String,
        @Field("telefono") telefono: String,
        @Field("apodo") apodo: String
    ): Response<List<ClienteMdl>>

    @FormUrlEncoded
    @PUT(ActCteUrl)
    suspend fun UpdateCte(
        @Field("id") id: Int,
        @Field("nombre") nombre: String,
        @Field("telefono") telefono: String,
        @Field("apodo") apodo: String
    ): Response<List<ClienteMdl>>

    @FormUrlEncoded
    @DELETE(DelCteUrl)
    suspend fun DeleteCte(
        @Field("id") id: Int
    ): Response<List<ClienteMdl>>

}