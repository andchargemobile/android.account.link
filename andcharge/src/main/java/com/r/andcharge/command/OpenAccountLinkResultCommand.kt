package com.r.andcharge.command

import androidx.appcompat.app.AppCompatActivity
import com.r.andcharge.base.Command
import com.r.andcharge.model.AccountLinkResult
import com.r.andcharge.view.AccountLinkResultDialog

/**
 * Shows [AccountLinkResult] by using [AccountLinkResultDialog]
 *
 * @property result the final result of the account linking, response from &Charge
 *
 * Author: romanvysotsky
 * Created: 08.07.20
 */

class OpenAccountLinkResultCommand(val result: AccountLinkResult) : Command {

    override fun execute(context: AppCompatActivity) {
        AccountLinkResultDialog.createAndShow(context.supportFragmentManager, result)
    }

}
