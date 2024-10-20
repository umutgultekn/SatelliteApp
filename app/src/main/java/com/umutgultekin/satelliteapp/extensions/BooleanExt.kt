package com.umutgultekin.satelliteapp.extensions

fun Boolean?.orFalse(): Boolean {
    return this ?: false
}