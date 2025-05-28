package com.sisterag.sisconven.servicios
import com.sisterag.sisconven.modelos.compras.CompraMdl
import com.sisterag.sisconven.utiles.EndPoint.ActCpraUrl
import com.sisterag.sisconven.utiles.EndPoint.AddCpraUrl
import com.sisterag.sisconven.utiles.EndPoint.DelCpraUrl
import com.sisterag.sisconven.utiles.EndPoint.GetAllCpraUrl
import com.sisterag.sisconven.utiles.EndPoint.GetCpraXidProvUrl
import com.sisterag.sisconven.utiles.EndPoint.GetCpraXidUrl
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET

import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiServiceCpra { /******** Compras *************/
    @GET(GetAllCpraUrl)
    suspend fun getCpraAll(): Response<List<CompraMdl>>

    @GET(GetCpraXidUrl)
    suspend fun getCpraById(
        @Path("id") id: String
    ): Response<CompraMdl>

    @GET(GetCpraXidProvUrl)
    suspend fun getCpraByIdProv(
        @Path("idprov") idprov: String
    ): Response<CompraMdl>

    @FormUrlEncoded
    @POST(AddCpraUrl)
    suspend fun CreateCpra(
        @Field("nombre") nombre: String,
        @Field("telefono") telefono: String,
        @Field("apodo") apodo: String
    ): Response<List<CompraMdl>>

    @FormUrlEncoded
    @PUT(ActCpraUrl)
    suspend fun UpdateCpra(
        @Field("id") id: Int,
        @Field("nombre") nombre: String,
        @Field("telefono") telefono: String,
        @Field("apodo") apodo: String
    ): Response<List<CompraMdl>>

    @FormUrlEncoded
    @DELETE(DelCpraUrl)
    suspend fun DeleteCpra(
        @Field("id") id: Int
    ): Response<List<CompraMdl>>
}