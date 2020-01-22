package com.babak.rates.data.source.remote

import android.util.Log
import com.babak.rates.data.Rates
import com.babak.rates.data.Result
import com.babak.rates.data.source.RatesDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RatesRemoteDataSource @Inject constructor(
    private val ratesService: RatesService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) :
    RatesDataSource {

    override suspend fun getAllRates(baseCurrency: String): Result<Rates?> {


        return withContext(ioDispatcher) {

            try {

                val rateResponse = ratesService.getRates(baseCurrency)
                return@withContext Result.Success(rateResponse.body())

            } catch (throwable: Throwable) {
                Log.e("Message", "Error", throwable)
                return@withContext when (throwable) {
                    is IOException -> Result.NetworkError
                    is HttpException -> {
                        val code = throwable.code()
                        val errorResponse = throwable.message()
                        Result.GenericError(code, errorResponse)
                    }
                    else -> {
                        Result.GenericError(null, null)
                    }
                }

            }


        }

    }

}