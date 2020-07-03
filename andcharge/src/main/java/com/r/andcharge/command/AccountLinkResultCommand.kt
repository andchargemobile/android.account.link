package com.r.andcharge.command

import androidx.appcompat.app.AppCompatActivity
import com.r.andcharge.dialog.AccountLinkResultDialog

/**
 * Shows the result of the account linking with &Charge
 *
 * Author: romanvysotsky
 * Created: 03.07.20
 */

class AccountLinkResultCommand(
    private val callbackUrl: String
) : Command {


    override fun execute(context: AppCompatActivity) {
        val accountLinkResultDialog = AccountLinkResultDialog()
        accountLinkResultDialog.setCallbackUrl(callbackUrl)
        accountLinkResultDialog.show(context.supportFragmentManager, AccountLinkResultDialog::class.java.name)
    }

}
