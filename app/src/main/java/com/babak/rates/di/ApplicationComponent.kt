package com.babak.rates.di

import android.content.Context
import com.babak.rates.RatesApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ApplicationModule::class,
        NetworkModule::class,
        RatesModule::class,
        RatesDataSourceModule::class
    ]
)
interface ApplicationComponent : AndroidInjector<RatesApplication> {


    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): ApplicationComponent
    }

}