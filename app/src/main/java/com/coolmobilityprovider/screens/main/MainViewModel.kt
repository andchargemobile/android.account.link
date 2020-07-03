package com.coolmobilityprovider.screens.main

import com.coolmobilityprovider.base.BaseViewModel
import com.coolmobilityprovider.base.SingleLiveEvent
import com.coolmobilityprovider.base.launch
import com.coolmobilityprovider.repository.CoolRepository
import com.r.andcharge.command.AccountLinkCompleteCommand
import com.r.andcharge.command.AccountLinkResultCommand
import com.r.andcharge.model.InitiateAccountLinkResponse
import org.koin.core.inject

/**
 * The screen's ViewModel where you link your user with &Charge
 *
 * Author: romanvysotsky
 * Created: 03.07.20
 */

class MainViewModel : BaseViewModel() {

    val accountLinkComplete: SingleLiveEvent<AccountLinkCompleteCommand> = SingleLiveEvent()
    val accountLinkShowResult: SingleLiveEvent<AccountLinkResultCommand> = SingleLiveEvent()

    val onError: SingleLiveEvent<Throwable> = SingleLiveEvent()


    private val coolRepository: CoolRepository by inject()


    /*
     * After AccountLinkCommand is executed and &Charge completed account linking, it will deep link
     * into your app with the provided callbackUrl. It will have additional query parameters attached.
     * AccountLinkResultCommand will open a dialog which will parse and present the result
     */
    fun onLinkAccountResultAvailable(callbackUrl: String) {
        val command = AccountLinkResultCommand(callbackUrl)
        accountLinkShowResult.postValue(command)
    }

    /*
     * When a user wants to link accounts, call your backend so it does the account linking
     */
    fun onLinkAccountClicked() {

        launch(
            coolRepository.initiateAndChargeAccountLink(),
            this::accountLinkCallSuccess,
            this::accountLinkCallFail
        )
    }

    /*
     * With the response of your backend, create an instance of OpenAndChargeCommand
     */
    private fun accountLinkCallSuccess(response: InitiateAccountLinkResponse) {
        val command = AccountLinkCompleteCommand(response)
        accountLinkComplete.postValue(command)
    }

    /*
     * Handle failed backend calls however you like
     */
    private fun accountLinkCallFail(error: Throwable) {
        onError.postValue(error)
    }

}
