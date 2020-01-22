package com.babak.rates.ui.rateconverter

import com.babak.rates.ui.model.Rate

interface RateAdapterInterface {
    fun onItemClick(rate: Rate)
    fun onTextChange(newValue: String)
}