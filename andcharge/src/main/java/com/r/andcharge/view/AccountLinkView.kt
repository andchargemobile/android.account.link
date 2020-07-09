package com.r.andcharge.view

import android.content.Intent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.r.andcharge.command.OpenAccountLinkInitCommand
import com.r.andcharge.command.OpenAccountLinkResultCommand
import com.r.andcharge.model.AccountLinkInit

/**
 * View for AccountLink,
 * showing a result dialog when the account link is completed
 * and showing the &Charge app when an account link is initiated
 *
 * can pass custom Observers to onShowInit and onShowResult to, for example,
 * do an action before showing the result or applying custom view logic to AccountLinkResult
 *
 * Author: romanvysotsky
 * Created: 08.07.20
 */

class AccountLinkView(
    private val view: AppCompatActivity,
    onShowInit: Observer<OpenAccountLinkInitCommand> = Observer { it.execute(view) },
    onShowResult: Observer<OpenAccountLinkResultCommand> = Observer { it.execute(view) }
) {

    private val viewModel: AccountLinkViewModel
            by view.viewModels { AccountLinkViewModel.Factory(view.applicationContext) }


    init {
        viewModel.accountLinkInitCommand.observe(view, onShowInit)
        viewModel.accountLinkResultCommand.observe(view, onShowResult)
    }


    /*
     * Call when you got the response from your backend after initiating account links
     */
    fun showAccountLinkInit(response: AccountLinkInit) {
        viewModel.onInitAccountLink(response)
    }

    /*
     * Call onCreate and onNewIntent to check for link account results
     */
    fun showAccountLinkResult(intent: Intent?) {
        val intentData = intent?.data?.toString()
        viewModel.onIntentData(intentData)
    }


    /*
     * Reactive utility method
     */
    fun showAccountLinkInit(liveData: LiveData<AccountLinkInit>) {
        liveData.observe(view, Observer(this::showAccountLinkInit))
    }

}
