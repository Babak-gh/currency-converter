package com.babak.rates.ui.model

import java.text.DecimalFormat

data class Rate(val currency: String, val value: Double) {
    val formattedValue: String
        get() = DecimalFormat("##.####").format(value)
}