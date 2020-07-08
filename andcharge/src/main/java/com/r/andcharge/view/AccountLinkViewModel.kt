package com.r.andcharge.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.r.andcharge.base.Command
import com.r.andcharge.base.SingleLiveEvent
import com.r.andcharge.command.OpenAccountLinkInitCommand
import com.r.andcharge.command.OpenAccountLinkResultCommand
import com.r.andcharge.model.AccountLinkInit
import com.r.andcharge.util.AccountLinkParser
import com.r.andcharge.util.UrlParser

/**
 *
 * Author: romanvysotsky
 * Created: 08.07.20
 */

class AccountLinkViewModel(private val parser: AccountLinkParser) : ViewModel() {

    val accountLinkInitCommand: SingleLiveEvent<Command> = SingleLiveEvent()
    val accountLinkResultCommand: SingleLiveEvent<Command> = SingleLiveEvent()


    fun onIntentData(intentData: String?) {

        val result = parser.parseAccountLinkResult(intentData) ?: return
        val command = OpenAccountLinkResultCommand(result)
        accountLinkResultCommand.postValue(command)

    }

    fun onInitAccountLink(response: AccountLinkInit) {

        val accountLinkUrl = parser.parseAccountLinkInit(response)
        val command = OpenAccountLinkInitCommand(accountLinkUrl)
        accountLinkInitCommand.postValue(command)

    }


    class Factory(private val context: Context) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val urlParser = UrlParser.createInstance()
            val accountLinkParser = AccountLinkParser(context, urlParser)
            val viewModel = AccountLinkViewModel(accountLinkParser)
            return viewModel as T
        }

    }

}
