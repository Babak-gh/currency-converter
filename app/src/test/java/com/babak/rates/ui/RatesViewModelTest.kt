package com.babak.rates.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.babak.rates.ui.rateConvertor.RatesViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before

import org.junit.Rule

@ExperimentalCoroutinesApi
class RatesViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var ratesViewModel: RatesViewModel

/*    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()*/

    @Before
    fun setUp() {

        // ratesViewModel = RatesViewModel()
    }

    @After
    fun tearDown() {
    }
}