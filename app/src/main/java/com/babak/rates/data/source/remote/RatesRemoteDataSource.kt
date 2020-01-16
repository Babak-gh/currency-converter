package com.babak.rates.data.source.remote

import com.babak.rates.data.Rates
import com.babak.rates.data.Result
import com.babak.rates.data.source.RatesDataSource
import javax.inject.Inject

class RatesRemoteDataSource @Inject constructor() : RatesDataSource {
    override suspend fun getAllRates(): Result<Rates> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}