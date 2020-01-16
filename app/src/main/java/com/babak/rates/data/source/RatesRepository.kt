package com.babak.rates.data.source

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RatesRepository @Inject constructor(private val ratesDataSource: RatesDataSource) {
}