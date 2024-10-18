package com.umutgultekin.satelliteapp.extensions

import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun EditText.textChanges(): Flow<String> = callbackFlow {
    val textWatcher = addTextChangedListener { text ->
        trySend(text.toString())
    }
    awaitClose { removeTextChangedListener(textWatcher) }
}