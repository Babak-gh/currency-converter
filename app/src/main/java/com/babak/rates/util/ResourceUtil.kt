package com.babak.rates.util

import android.content.Context


fun getFlagResourceId(context: Context, symbol: String) = context.resources.getIdentifier(
    "ic_flag_$symbol", "drawable", context.packageName
)

fun getCurrencyNameResourceId(context: Context, symbol: String) =
    context.resources.getIdentifier(
        "currency_name_$symbol", "string",
        context.packageName
    )