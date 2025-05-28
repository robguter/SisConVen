package com.sisterag.sisconven.servicios

import com.sisterag.sisconven.modelos.inventario.UploadResponse
import com.sisterag.sisconven.utiles.EndPoint.AddArtUrl
import com.sisterag.sisconven.utiles.EndPoint.BASE_URL

import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body

import retrofit2.http.Headers

import retrofit2.http.POST
import retrofit2.http.Part

interface MiAPI {

    @Headers("Accept: application/json")
    @POST(AddArtUrl)
    fun uploadImage(
        @Body requestBody: RequestBody
    ): Call<UploadResponse>

    companion object {
        // Create HTTP Client for network request
        private val client = OkHttpClient.Builder().build()

        fun uploadApi(): MiAPI {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(MiAPI::class.java)
        }
    }
}