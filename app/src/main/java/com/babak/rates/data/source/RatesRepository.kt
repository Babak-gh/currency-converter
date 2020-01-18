package com.babak.rates.data.source

import com.babak.rates.data.Rates
import com.babak.rates.data.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RatesRepository @Inject constructor(
    private val ratesDataSource: RatesDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun getAllRates(baseCurrency: String): Result<Rates?> {

        return withContext(ioDispatcher) {

            val rateResponse = ratesDataSource.getAllRates()

            if (rateResponse.isSuccessful) {
                return@withContext Result.Success(rateResponse.body())
            } else {
                return@withContext Result.Error(Exception(rateResponse.errorBody().toString()))
            }
        }
    }
}