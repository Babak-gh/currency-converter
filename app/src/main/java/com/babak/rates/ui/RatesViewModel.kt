package com.babak.rates.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.babak.rates.data.Result
import com.babak.rates.data.source.RatesRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class RatesViewModel @Inject constructor(private val ratesRepository: RatesRepository) :
    ViewModel() {

    private val _items = MutableLiveData<List<Rate>>().apply { value = emptyList() }
    val items: LiveData<List<Rate>> = _items

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading


    init {
        startFetchingRates()
    }

    private fun startFetchingRates() {
        viewModelScope.launch {
            ratesRepository.getAllRates("EUR").let { result ->
                if (result is Result.Success) {
                    val tempList = mutableListOf<Rate>()
                    result.data?.rates?.map {
                        tempList.add(Rate(it.key, it.value))
                    }
                    _items.postValue(tempList)
                } else {

                }

            }
        }
    }

}