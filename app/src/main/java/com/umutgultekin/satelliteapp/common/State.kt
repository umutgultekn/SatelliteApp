package com.umutgultekin.satelliteapp.common

sealed class State<T>(val data: T? = null, val message: String? = null) {
    class Idle<T> : State<T>()
    class Loading<T>(data: T? = null) : State<T>(data)
    class Success<T>(data: T) : State<T>(data)
    class Error<T>(message: String? = null, data: T? = null) : State<T>(data, message)
}