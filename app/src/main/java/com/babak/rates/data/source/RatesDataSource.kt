package com.babak.rates.data.source

import com.babak.rates.data.Rates
import retrofit2.Response

interface RatesDataSource {

    suspend fun getAllRates(): Response<Rates>
}