package com.babak.rates.data.source

import com.babak.rates.data.Rates
import com.babak.rates.data.Result

interface RatesRepository {

    suspend fun getAllRates(baseCurrency: String): Result<Rates?>

}