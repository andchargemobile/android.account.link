package com.r.andcharge.view

import android.content.Intent
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.r.andcharge.command.OpenAccountLinkInitCommand
import com.r.andcharge.command.OpenAccountLinkResultCommand
import com.r.andcharge.model.AccountLinkInit
import com.r.andcharge.model.AccountLinkResult

/**
 * View for AccountLink,
 * showing a result dialog when the account link is completed
 * and showing the &Charge app when an account link is initiated
 *
 * Usage:
 *
 * Create an instance in onCreate:
 * val view = AccountLinkView(this)
 *
 * Pass intent in onCreate (and onNewIntent if using android:launchMode="singleTask"):
 * view.showAccountLinkResult(intent)
 *
 * Pass init account link result when its ready:
 * view.showAccountLinkInit(result)
 *
 *
 * @property view activity which receives the &Charge deep links
 * @constructor you can pass custom Observers to onShowInit and onShowResult to, for example,
 * do an action before showing the [AccountLinkResult] or applying custom view logic to
 *
 * Author: romanvysotsky
 * Created: 08.07.20
 */

class AccountLinkView(
    private val view: FragmentActivity,
    onShowInit: Observer<OpenAccountLinkInitCommand> = Observer { it.execute(view) },
    onShowResult: Observer<OpenAccountLinkResultCommand> = Observer { it.execute(view) }
) {

    private val viewModel: AccountLinkViewModel
            by view.viewModels { AccountLinkViewModel.Factory(view.applicationContext) }


    init {
        viewModel.accountLinkInitCommand.observe(view, onShowInit)
        viewModel.accountLinkResultCommand.observe(view, onShowResult)
    }


    /**
     * Parses the given [response] and deep links into &Charge.
     * The properties of [response] are provided by your backend after initiating an account link
     */
    fun showAccountLinkInit(response: AccountLinkInit) {
        viewModel.onInitAccountLink(response)
    }

    /**
     * Parses the [intent] and opens a dialog to show the result of account linking with &Charge
     * Call onCreate and onNewIntent to check for link account results
     */
    fun showAccountLinkResult(intent: Intent?) {
        val intentData = intent?.data?.toString()
        viewModel.onIntentData(intentData)
    }


    /**
     * Reactive alternative to [showAccountLinkInit]
     */
    fun showAccountLinkInit(liveData: LiveData<AccountLinkInit>) {
        liveData.observe(view, Observer(this::showAccountLinkInit))
    }

}
