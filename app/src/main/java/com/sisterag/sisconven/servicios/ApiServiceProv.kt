package com.sisterag.sisconven.servicios
import com.sisterag.sisconven.modelos.compras.ProveeMdl
import com.sisterag.sisconven.modelos.compras.ProveedorMdl
import com.sisterag.sisconven.utiles.EndPoint.ActProvUrl
import com.sisterag.sisconven.utiles.EndPoint.AddProvUrl
import com.sisterag.sisconven.utiles.EndPoint.DelProvUrl
import com.sisterag.sisconven.utiles.EndPoint.GetAllProvUrl
import com.sisterag.sisconven.utiles.EndPoint.GetProvXidUrl
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiServiceProv {
    @GET(GetAllProvUrl)
    suspend fun getProvAll(): Response<List<ProveedorMdl>>

    @GET(GetAllProvUrl)
    suspend fun getProvAllCbo(): Response<List<ProveeMdl>>

    @GET(GetProvXidUrl)
    suspend fun getProvById(
        @Path("id") id: String
    ): Response<ProveedorMdl>

    @FormUrlEncoded
    @POST(AddProvUrl)
    suspend fun CreateProv(
        @Field("cedrif") cedrif: String,
        @Field("nombre") nombre: String,
        @Field("telefono") telefono: String
    ): Response<List<ProveedorMdl>>

    @FormUrlEncoded
    @PUT(ActProvUrl)
    suspend fun UpdateProv(
        @Field("id") id: Int,
        @Field("cedrif") cedrif: String,
        @Field("nombre") nombre: String,
        @Field("telefono") telefono: String
    ): Response<List<ProveedorMdl>>

    @FormUrlEncoded
    @DELETE(DelProvUrl)
    suspend fun DeleteProv(
        @Field("id") id: Int
    ): Response<List<ProveedorMdl>>
}