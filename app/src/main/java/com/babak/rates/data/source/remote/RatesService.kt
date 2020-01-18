package com.babak.rates.data.source.remote

import com.babak.rates.data.Rates
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RatesService {

    @GET("latest")
    suspend fun getRates(@Query("base") base: String): Response<Rates>
}