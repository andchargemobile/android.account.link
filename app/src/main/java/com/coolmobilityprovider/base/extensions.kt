package com.coolmobilityprovider.base

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewModelScope
import com.coolmobilityprovider.R
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

fun AppCompatActivity.showError(error: Throwable) {
    val errorAsString = error.javaClass.simpleName + (error.message ?: "")
    val errorText = this.getString(R.string.main_error, errorAsString)
    val toast = Toast.makeText(this, errorText, Toast.LENGTH_LONG)
    toast.show()
}

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
