package com.coolmobilityprovider.screens.main

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.coolmobilityprovider.base.BaseViewModel
import com.coolmobilityprovider.base.SingleLiveEvent
import com.coolmobilityprovider.base.launch
import com.coolmobilityprovider.repository.CoolRepository
import com.r.andcharge.command.CompleteAccountLinkCommand
import com.r.andcharge.model.AccountLinkResult
import com.r.andcharge.model.InitiateAccountLinkResponse
import com.r.andcharge.util.AndChargeCallbackUrlParser
import org.koin.core.inject

/**
 * The screen's ViewModel where you link your user with &Charge
 *
 * Author: romanvysotsky
 * Created: 03.07.20
 */

class MainViewModel(callbackUrlResult: String?) : BaseViewModel() {

    val accountLinkInitiated: SingleLiveEvent<CompleteAccountLinkCommand> = SingleLiveEvent()
    val accountLinkResult: SingleLiveEvent<AccountLinkResult> = SingleLiveEvent()
    val onError: SingleLiveEvent<Throwable> = SingleLiveEvent()

    private val coolRepository: CoolRepository by inject()
    private val callbackUrlParser: AndChargeCallbackUrlParser by inject()


    /*
     * parse the callback url from the intent data to an AccountLinkResult with AndChargeCallbackUrlParser
     */
    init {

        val result = callbackUrlParser.getAccountLinkResultOrNull(callbackUrlResult)
        if(result != null) {
            accountLinkResult.postValue(result)
        }
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
     * With the response of your repository, create an instance of OpenAndChargeCommand
     */
    private fun accountLinkCallSuccess(response: InitiateAccountLinkResponse) {
        val command = CompleteAccountLinkCommand(response)
        accountLinkInitiated.postValue(command)
    }

    /*
     * Handle failed backend calls however you like
     */
    private fun accountLinkCallFail(error: Throwable) {
        onError.postValue(error)
    }



    class Factory(private val intent: Intent?) : ViewModelProvider.Factory {

        /*
         * After AccountLinkCommand is executed and &Charge completed account linking, it will deep link
         * into your app with the provided callbackUrl. The url will have additional query parameters attached
         */
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel(intent?.data?.toString()) as T
        }

    }

}
