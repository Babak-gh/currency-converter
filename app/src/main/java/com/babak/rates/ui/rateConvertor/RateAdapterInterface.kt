package com.babak.rates.ui.rateConvertor

import com.babak.rates.ui.model.Rate

interface RateAdapterInterface {
    fun onItemClick(rate: Rate)
    fun onTextChange(newValue: String)
}