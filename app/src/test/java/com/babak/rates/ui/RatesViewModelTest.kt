package com.babak.rates.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.babak.rates.LiveDataTestUtil
import com.babak.rates.MainCoroutineRule
import com.babak.rates.data.Result
import com.babak.rates.data.source.RatesRepository
import com.babak.rates.ui.rateConvertor.RatesViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class RatesViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var ratesViewModel: RatesViewModel

    private var rateRepository = mockk<RatesRepository>()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val baseValue = 1.0
    private val baseCurrency = "GBP"

    @Before
    fun setUp() {

    }

    @After
    fun tearDown() {
    }

    @Test
    fun defaultValueOfRatesLiveData() {


        coEvery { rateRepository.getAllRates(any()) } returns Result.Success(null)

        ratesViewModel = RatesViewModel(rateRepository)


        ratesViewModel.startFetchingRates(baseCurrency, baseValue)

        assertNotNull(LiveDataTestUtil.getValue(ratesViewModel.items))

    }

    @Test
    fun defaultValueOfIsChangeCurrencyLiveData() {


        coEvery { rateRepository.getAllRates(any()) } returns Result.Success(null)

        ratesViewModel = RatesViewModel(rateRepository)


        ratesViewModel.startFetchingRates(baseCurrency, baseValue)

        assertFalse(LiveDataTestUtil.getValue(ratesViewModel.isChangeCurrency))

    }

    @Test
    fun changeBaseCurrency() {


        coEvery { rateRepository.getAllRates(any()) } returns Result.Success(null)

        ratesViewModel = RatesViewModel(rateRepository)


        ratesViewModel.changeBaseCurrency("")

        assertNotNull(LiveDataTestUtil.getValue(ratesViewModel.items))
        assertTrue(LiveDataTestUtil.getValue(ratesViewModel.isChangeCurrency))
        assertFalse(LiveDataTestUtil.getValue(ratesViewModel.dataLoading))

    }

    @Test
    fun changeValue() {
        coEvery { rateRepository.getAllRates(any()) } returns Result.Success(null)

        ratesViewModel = RatesViewModel(rateRepository)


        ratesViewModel.valueChanged("2.336")

        assertNotNull(LiveDataTestUtil.getValue(ratesViewModel.items))
        assertNotEquals(baseValue, ratesViewModel.baseValue)
        assertFalse(LiveDataTestUtil.getValue(ratesViewModel.isChangeCurrency))
        assertFalse(LiveDataTestUtil.getValue(ratesViewModel.dataLoading))
    }

}