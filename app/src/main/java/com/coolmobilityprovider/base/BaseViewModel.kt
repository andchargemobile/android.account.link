package com.coolmobilityprovider.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.koin.core.KoinComponent

/**
 *
 * Author: romanvysotsky
 * Created: 03.07.20
 */

open class BaseViewModel : ViewModel(), KoinComponent {

    val isLoading: MutableLiveData<Boolean> = MutableLiveData()

}
