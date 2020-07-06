package com.coolmobilityprovider.base

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 *
 * Author: romanvysotsky
 * Created: 03.07.20
 */

fun <T> BaseViewModel.launch(
    flow: Flow<T>,
    onSuccess: (T) -> Unit,
    onError: (Throwable) -> Unit
): Job {

    return flow.flowOn(Dispatchers.IO)
        .onStart { isLoading.postValue(true) }
        .onCompletion { isLoading.postValue(false) }
        .onEach { onSuccess(it) }
        .catch { onError(it) }
        .launchIn(viewModelScope)
}

inline fun <reified T> getKoinInstance(): T {
    return object : KoinComponent {
        val value: T by inject()
    }.value
}
