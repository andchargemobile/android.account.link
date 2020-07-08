package com.r.andcharge.view

import android.content.Intent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.r.andcharge.base.Command
import com.r.andcharge.model.AccountLinkInit

/**
 *
 * Author: romanvysotsky
 * Created: 08.07.20
 */

class AccountLinkView(private val view: AppCompatActivity) {

    private val viewModel: AccountLinkViewModel
            by view.viewModels { AccountLinkViewModel.Factory(view.applicationContext) }


    init {
        val commandObserver = Observer<Command> { it.execute(view) }
        viewModel.accountLinkInitCommand.observe(view, commandObserver)
        viewModel.accountLinkResultCommand.observe(view, commandObserver)
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
