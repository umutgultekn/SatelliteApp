package com.umutgultekin.satelliteapp.extensions

import com.umutgultekin.satelliteapp.common.Constants.EMPTY_STRING
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

fun Long?.orZero(): Long {
    return this ?: 0L
}

fun Long?.toFormattedString(): String = runCatching {
    val symbols = DecimalFormatSymbols(Locale.getDefault()).apply {
        groupingSeparator = '.'
    }
    val decimalFormat = DecimalFormat("#,###", symbols)
    decimalFormat.format(this)
}.getOrElse { EMPTY_STRING }