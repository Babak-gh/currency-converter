package com.babak.rates.di

import androidx.lifecycle.ViewModel
import com.babak.rates.ui.rateconverter.RatesFragment
import com.babak.rates.ui.rateconverter.RatesViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class RatesModule {

    @ContributesAndroidInjector(
        modules = [
            ViewModelBuilder::class
        ]
    )
    internal abstract fun ratesFragment(): RatesFragment

    @Binds
    @IntoMap
    @ViewModelKey(RatesViewModel::class)
    abstract fun bindViewModel(viewmodel: RatesViewModel): ViewModel

}