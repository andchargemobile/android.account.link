package com.r.andcharge.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.r.andcharge.base.SingleLiveEvent
import com.r.andcharge.command.OpenAccountLinkInitCommand
import com.r.andcharge.command.OpenAccountLinkResultCommand
import com.r.andcharge.model.AccountLinkInit
import com.r.andcharge.util.AccountLinkParser
import com.r.andcharge.util.UrlParser

/**
 * Responsible for
 * parsing String (&Charge callback url after completing account link) -> OpenAccountLinkResultCommand
 * parsing AccountLinkInit -> OpenAccountLinkInitCommand
 *
 * @property parser utility class for parsing
 *
 * Author: romanvysotsky
 * Created: 08.07.20
 */

class AccountLinkViewModel(private val parser: AccountLinkParser) : ViewModel() {

    val accountLinkInitCommand: SingleLiveEvent<OpenAccountLinkInitCommand> = SingleLiveEvent()
    val accountLinkResultCommand: SingleLiveEvent<OpenAccountLinkResultCommand> = SingleLiveEvent()


    /**
     * Posts an [OpenAccountLinkResultCommand] if the [intentData] is a valid callback url
     *
     * @param intentData a callback url from &Charge into your app
     */
    fun onIntentData(intentData: String?) {

        val result = parser.parseAccountLinkResult(intentData) ?: return
        val command = OpenAccountLinkResultCommand(result)
        accountLinkResultCommand.postValue(command)

    }

    /**
     * Posts [OpenAccountLinkInitCommand] containing the deep link into &Charge with the values from [response]
     *
     * @param response the result of initiating an account link
     */
    fun onInitAccountLink(response: AccountLinkInit) {

        val accountLinkUrl = parser.parseAccountLinkInit(response)
        val command = OpenAccountLinkInitCommand(accountLinkUrl)
        accountLinkInitCommand.postValue(command)

    }


    class Factory(private val context: Context) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val urlParser = UrlParser.createInstance()
            val accountLinkParser = AccountLinkParser.createInstance(context, urlParser)
            val viewModel = AccountLinkViewModel(accountLinkParser)
            return viewModel as T
        }

    }

}
