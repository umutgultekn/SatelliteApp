package com.umutgultekin.satelliteapp.extensions

import java.text.SimpleDateFormat
import java.util.Locale


fun String?.toDateFormat(): String? {
    val originalFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val desiredFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

    return runCatching {
        this?.let { original ->
            originalFormat.parse(original)?.let { desiredFormat.format(it) }
        }
    }.getOrNull()
}