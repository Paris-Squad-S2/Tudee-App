package com.example.tudeeapp.presentation.utills

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

fun Int.toLocalizedString(): String {
    return if (Locale.getDefault().language == "ar") {
        val arabicLocale = Locale("ar")
        val symbols = DecimalFormatSymbols(arabicLocale)
        val formatter = DecimalFormat("#", symbols)
        formatter.format(this)
    } else {
        this.toString()
    }
}

fun String.localizeNumbers(): String {
    return this.replace(Regex("\\d+")) { matchResult ->
        val number = matchResult.value.toIntOrNull()
        number?.toLocalizedString() ?: matchResult.value
    }
}