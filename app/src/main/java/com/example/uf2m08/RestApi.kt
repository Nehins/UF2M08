package com.example.uf2m08

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import retrofit2.http.DELETE


interface RestApi {

    @GET("/cicles/{Id}")
    suspend fun getCicle(@Path("Id") Id: Number): Response<ResponseBody>

    @GET("/qualificacions?nota_gte=5&nota_lte=10&modul=1")
    suspend fun getQualificacions(): Response<ResponseBody>

    @DELETE("/alumnes/{Id}")
    fun deleteAlumne(@Path("Id") Id: String):  Call<AlumneInfo>

    @PUT("/alumnes/{Id}")
    fun cambiarAlumne(@Path("Id") Id: String, @Body alumePost : AlumneInfo):  Call<AlumneInfo>

}