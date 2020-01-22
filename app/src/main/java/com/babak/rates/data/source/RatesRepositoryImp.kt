package com.babak.rates.data.source

import com.babak.rates.data.Rates
import com.babak.rates.data.Result

import javax.inject.Inject

class RatesRepositoryImp @Inject constructor(
    private val ratesDataSource: RatesDataSource
) : RatesRepository {

    override suspend fun getAllRates(baseCurrency: String): Result<Rates?> =
        ratesDataSource.getAllRates(baseCurrency)
}