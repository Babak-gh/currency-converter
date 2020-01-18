package com.babak.rates.di

import com.babak.rates.data.source.remote.RatesService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {


    @Singleton
    @Provides
    fun provideRetrofitService(
        client: OkHttpClient,
        factory: Converter.Factory,
        @NetworkURLQualifier networkURL: String
    ): RatesService {
        return Retrofit.Builder()
            .baseUrl(networkURL)
            .client(client)
            .addConverterFactory(factory)
            .build()
            .create(RatesService::class.java)
    }

    @Provides
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()


    @Provides
    fun provideConverterFactory(): Converter.Factory =
        GsonConverterFactory.create()


    @Provides
    @NetworkURLQualifier
    fun provideNetworkURL() = "https://revolut.duckdns.org/"

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().also {
            it.level = HttpLoggingInterceptor.Level.BODY
        }

}