package com.babak.rates.di

import com.babak.rates.RatesApplication
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton


@Singleton
@Component(modules = [AndroidInjectionModule::class, NetworkModule::class])
interface ApplicationComponent : AndroidInjector<RatesApplication> {


}