package com.umutgultekin.satelliteapp.extensions

fun Double?.orZero(): Double {
    return this ?: 0.0
}