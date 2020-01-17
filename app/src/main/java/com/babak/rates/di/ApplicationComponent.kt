package com.babak.rates.di

import com.babak.rates.RatesApplication
import com.babak.rates.data.source.RatesRepository
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, NetworkModule::class, RatesModule::class, RatesDataSourceModule::class])
interface ApplicationComponent : AndroidInjector<RatesApplication> {


/*    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): ApplicationComponent
    }*/

    fun ratesRepository(): RatesRepository

}