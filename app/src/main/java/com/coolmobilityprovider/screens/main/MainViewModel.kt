package com.coolmobilityprovider.screens.main

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.coolmobilityprovider.base.BaseViewModel
import com.coolmobilityprovider.base.SingleLiveEvent
import com.coolmobilityprovider.base.launch
import com.coolmobilityprovider.repository.CoolRepository
import com.r.andcharge.command.OpenLinkCommand
import com.r.andcharge.model.AccountLinkResult
import com.r.andcharge.model.InitiateAccountLinkResponse
import com.r.andcharge.util.AndChargeUrlParser
import org.koin.core.inject

/**
 * The screen's ViewModel where you link your user with &Charge
 *
 * Author: romanvysotsky
 * Created: 03.07.20
 */

class MainViewModel(val callbackUrlResult: String?) : BaseViewModel() {

    val updateAndChargeAccountLinkUrl: MutableLiveData<Unit> = MutableLiveData(Unit)

    val accountLinkInitiated: SingleLiveEvent<OpenLinkCommand> = SingleLiveEvent()
    val accountLinkResult: SingleLiveEvent<AccountLinkResult> = SingleLiveEvent()
    val onError: SingleLiveEvent<Throwable> = SingleLiveEvent()

    private val coolRepository: CoolRepository by inject()
    private val urlParser: AndChargeUrlParser by inject()


    /*
     * parse the callback url from the intent data to an AccountLinkResult with AndChargeCallbackUrlParser
     */
    init {

        val result = urlParser.parseCallbackUrl(callbackUrlResult)
        if(result != null) {
            accountLinkResult.postValue(result)
        }
    }

    /*
    for demonstration purposes only; get the parameters from the edit texts and
    show which url would be called when clicking the "LINK ACCOUNT" button
     */
    fun notifyAnyTextChanged() {
        updateAndChargeAccountLinkUrl.postValue(Unit)
    }


    /*
     * When a user wants to link accounts, call your repository so it initiates the account linking
     */
    fun onInitiateAccountLinkClicked() {

        launch(
            coolRepository.initiateAndChargeAccountLink(),
            this::accountLinkCallSuccess,
            this::accountLinkCallFail
        )
    }

    /*
     * Handle the response by creating an accountLinkUrl and opening it
     */
    private fun accountLinkCallSuccess(response: InitiateAccountLinkResponse) {
        val accountLinkUrl = urlParser.createAccountLinkUrl(response)
        val command = OpenLinkCommand(accountLinkUrl)
        accountLinkInitiated.postValue(command)
    }

    /*
     * Handle failed backend calls however you like
     */
    private fun accountLinkCallFail(error: Throwable) {
        onError.postValue(error)
    }



    class Factory(private val intent: Intent?) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel(intent?.data?.toString()) as T
        }

    }

}
