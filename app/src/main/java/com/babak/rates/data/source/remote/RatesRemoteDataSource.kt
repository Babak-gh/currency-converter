package com.babak.rates.data.source.remote

import com.babak.rates.data.Rates
import com.babak.rates.data.source.RatesDataSource
import retrofit2.Response
import javax.inject.Inject

class RatesRemoteDataSource @Inject constructor(private val ratesService: RatesService) :
    RatesDataSource {

    override suspend fun getAllRates(baseCurrency: String): Response<Rates> =
        ratesService.getRates(baseCurrency)
}