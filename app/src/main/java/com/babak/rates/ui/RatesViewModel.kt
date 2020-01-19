package com.babak.rates.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.babak.rates.data.Result
import com.babak.rates.data.source.RatesRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class RatesViewModel @Inject constructor(private val ratesRepository: RatesRepository) :
    ViewModel() {

    private val _items = MutableLiveData<List<Rate>>().apply { value = emptyList() }
    val items: LiveData<List<Rate>> = _items

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading


    private var job: Job? = null
    private var baseValue = 1.0
    private var baseCurrency = "EUR"

    init {
        startFetchingRates(baseCurrency, baseValue)
    }

    private fun startFetchingRates(baseCurrency: String, baseValue: Double) {
        job = viewModelScope.launch {
            while (true) {
                ratesRepository.getAllRates(baseCurrency).let { result ->
                    if (result is Result.Success) {
                        val tempList = mutableListOf<Rate>()
                        tempList.add(Rate(baseCurrency, baseValue))
                        result.data?.rates?.map {
                            tempList.add(Rate(it.key, it.value * baseValue))
                        }
                        _items.postValue(tempList)
                    } else {

                    }

                }
                delay(1000)
            }
        }
    }

    fun changeBaseCurrency(currency: String) {
        job?.cancel()
        baseCurrency = currency
        startFetchingRates(baseCurrency, baseValue)
    }

    fun valueChanged(newValue: String) {
        job?.cancel()
        baseValue = newValue.toDouble()
        startFetchingRates(baseCurrency, baseValue)
    }

}