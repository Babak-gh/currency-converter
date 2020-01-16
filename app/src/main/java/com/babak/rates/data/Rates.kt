package com.babak.rates.data

data class Rates(
    val base: String,
    val date: String,
    val rates: HashMap<String, Double>
)