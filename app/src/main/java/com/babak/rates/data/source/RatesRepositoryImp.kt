package com.babak.rates.data.source

import com.babak.rates.data.Rates
import com.babak.rates.data.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RatesRepositoryImp @Inject constructor(
    private val ratesDataSource: RatesDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : RatesRepository {

    override suspend fun getAllRates(baseCurrency: String): Result<Rates?> {

        return withContext(ioDispatcher) {

            val rateResponse = ratesDataSource.getAllRates(baseCurrency)

            if (rateResponse.isSuccessful) {
                return@withContext Result.Success(rateResponse.body())
            } else {
                return@withContext Result.Error(Exception(rateResponse.errorBody().toString()))
            }
        }
    }
}