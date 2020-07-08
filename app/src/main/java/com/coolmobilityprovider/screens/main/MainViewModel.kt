package com.coolmobilityprovider.screens.main

import androidx.lifecycle.MutableLiveData
import com.coolmobilityprovider.base.BaseViewModel
import com.coolmobilityprovider.base.launch
import com.coolmobilityprovider.repository.CoolRepository
import com.r.andcharge.base.SingleLiveEvent
import com.r.andcharge.model.AccountLinkInit
import org.koin.core.inject

/**
 * The screen's ViewModel where you link your user with &Charge
 *
 * Author: romanvysotsky
 * Created: 03.07.20
 */

class MainViewModel : BaseViewModel() {

    val updateAccountLinkInitUrl: MutableLiveData<Unit> = MutableLiveData(Unit)


    val onAccountLinkInitiated: SingleLiveEvent<AccountLinkInit> = SingleLiveEvent()
    val onError: SingleLiveEvent<Throwable> = SingleLiveEvent()

    private val coolRepository: CoolRepository by inject()


    /*
     * For demonstration purposes only; this triggers the databinding;
     * gets last typed parameters in the edits texts and shows which url would be called when
     * clicking the "LINK ACCOUNT" button
     */
    fun notifyAnyTextChanged() {
        updateAccountLinkInitUrl.postValue(Unit)
    }


    /*
     * When a user wants to link accounts, call your repository so it initiates the account linking
     * Post the response on success
     * Handle your backend error however you need on error
     */
    fun onAccountLinkInitiateClicked() {

        launch(
            coolRepository.initiateAndChargeAccountLink(),
            { onAccountLinkInitiated.postValue(it) },
            { onError.postValue(it) }
        )
    }

}
