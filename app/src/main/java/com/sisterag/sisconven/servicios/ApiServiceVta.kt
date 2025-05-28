package com.sisterag.sisconven.servicios
import com.sisterag.sisconven.modelos.ventas.ClienteMdl
import com.sisterag.sisconven.utiles.EndPoint.ActCpraUrl
import com.sisterag.sisconven.utiles.EndPoint.ActCteUrl
import com.sisterag.sisconven.utiles.EndPoint.ActVntaUrl
import com.sisterag.sisconven.utiles.EndPoint.AddCpraUrl
import com.sisterag.sisconven.utiles.EndPoint.AddCteUrl
import com.sisterag.sisconven.utiles.EndPoint.AddVntaUrl
import com.sisterag.sisconven.utiles.EndPoint.DelCpraUrl
import com.sisterag.sisconven.utiles.EndPoint.DelCteUrl
import com.sisterag.sisconven.utiles.EndPoint.DelVntaUrl
import com.sisterag.sisconven.utiles.EndPoint.GetAllCpraUrl
import com.sisterag.sisconven.utiles.EndPoint.GetAllCteUrl
import com.sisterag.sisconven.utiles.EndPoint.GetAllVntaUrl
import com.sisterag.sisconven.utiles.EndPoint.GetCpraXidUrl
import com.sisterag.sisconven.utiles.EndPoint.GetCteXidUrl
import com.sisterag.sisconven.utiles.EndPoint.GetVntaXidUrl
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiServiceVta { /******** CLIENTAS *************/
    @GET(GetAllVntaUrl)
    suspend fun getVntaAll(): Response<List<ClienteMdl>>

    @GET(GetVntaXidUrl)
    suspend fun getVntaById(
        @Path("id") id: String
    ): Response<ClienteMdl>

    @FormUrlEncoded
    @POST(AddVntaUrl)
    suspend fun CreateVnta(
        @Field("nombre") nombre: String,
        @Field("telefono") telefono: String,
        @Field("apodo") apodo: String
    ): Response<List<ClienteMdl>>

    @FormUrlEncoded
    @PUT(ActVntaUrl)
    suspend fun UpdateVnta(
        @Field("id") id: Int,
        @Field("nombre") nombre: String,
        @Field("telefono") telefono: String,
        @Field("apodo") apodo: String
    ): Response<List<ClienteMdl>>

    @FormUrlEncoded
    @DELETE(DelVntaUrl)
    suspend fun DeleteVnta(
        @Field("id") id: Int
    ): Response<List<ClienteMdl>>
}