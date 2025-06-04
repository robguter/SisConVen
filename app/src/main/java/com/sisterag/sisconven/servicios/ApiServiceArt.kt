package com.sisterag.sisconven.servicios

import com.sisterag.sisconven.modelos.inventario.ArtResponse
import com.sisterag.sisconven.modelos.inventario.ArticuloDto
import com.sisterag.sisconven.modelos.inventario.UploadResponse
import com.sisterag.sisconven.utiles.EndPoint.ActArtUrl
import com.sisterag.sisconven.utiles.EndPoint.AddArtUrl
import com.sisterag.sisconven.utiles.EndPoint.AddArtUrli
import com.sisterag.sisconven.utiles.EndPoint.DelArtUrl
import com.sisterag.sisconven.utiles.EndPoint.GetAllArtUrl
import com.sisterag.sisconven.utiles.EndPoint.GetArtXidUrl
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call

import retrofit2.Response

import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServiceArt {
    @GET(GetAllArtUrl)
    suspend fun getArtAll():
    Response<List<ArticuloDto>>

    @GET(GetAllArtUrl)
    suspend fun getArtAllCbo(): Response<List<ArticuloDto>>

    @GET(GetArtXidUrl)
    suspend fun getArtById(
        @Path("id") id: String
    ): Response<ArticuloDto>

    @POST(AddArtUrli)
    @Multipart
    suspend fun uploadImage(
        @Part file: MultipartBody.Part,
        @Query("fileType")fileType: String,
        @Query("userId") userId: String
    ):Response<Any>

    @Headers(
        "Accept:application/json"
    )
    @Multipart
    @POST(AddArtUrli) // Ruta de la API en tu servidor
    suspend fun uploadImage(
        @Part("idcat") idcat: RequestBody,
        @Part("articulo") articulo: RequestBody,
        @Part("marca") marca: RequestBody,
        @Part("descripcion") descripcion: RequestBody,
        @Part imagen: MultipartBody.Part
    ): Response<ArtResponse>

    @FormUrlEncoded
    @POST(AddArtUrl)
    suspend fun CreateArt(
        @Field("idcat") idcat: Int,
        @Field("articulo") articulo: String,
        @Field("marca") marca: String,
        @Field("descripcion") descripcion: String,
        @Field("image") image: String
    ): Response<List<ArticuloDto>>

    @FormUrlEncoded
    @PUT(ActArtUrl)
    suspend fun UpdateArt(
        @Field("id") id: Int,
        @Field("idcat") idcat: Int,
        @Field("articulo") articulo: String,
        @Field("marca") marca: String,
        @Field("descripcion") descripcion: String,
        @Field("image") image: String
    ): Response<List<ArticuloDto>>

    @FormUrlEncoded
    @DELETE(DelArtUrl)
    suspend fun DeleteArt(
        @Field("id") id: Int
    ): Response<List<ArticuloDto>>

}