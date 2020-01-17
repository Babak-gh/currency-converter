package com.babak.rates.di

import com.babak.rates.data.source.RatesDataSource
import com.babak.rates.data.source.remote.RatesRemoteDataSource
import dagger.Binds
import dagger.Module

@Module
abstract class RatesDataSourceModule {

    @Binds
    abstract fun provideDataSource(ratesDataSource: RatesRemoteDataSource): RatesDataSource

}