package com.nadiabsag.room

import retrofit2.Response
import retrofit2.http.GET


interface ApiService {

    @GET("/api/4225765517649804/search/sp")
    suspend fun getSuperheroes(): Response<SuperheroDataResponse>

    @GET("/api/4225765517649804/search/sp")
    suspend fun getSuperheroDetail(): Response<SuperheroDetailResponse>

}