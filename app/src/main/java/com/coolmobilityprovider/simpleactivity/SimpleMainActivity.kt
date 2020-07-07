package com.coolmobilityprovider.simpleactivity

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.coolmobilityprovider.R
import com.r.andcharge.command.OpenLinkCommand
import com.r.andcharge.dialog.AccountLinkResultDialog
import com.r.andcharge.model.InitiateAccountLinkResponse
import com.r.andcharge.util.AndChargeUrlParser

/**
 * Represents the screen where you link your user with &Charge.
 * Instead of MainActivity, this shows everything coming together in one place
 *
 *

How to set up:

    redefine the callback url scheme & host strings in your ids.xml or strings.xml to fit your requirements
        <string name="andcharge_callback_scheme" translatable="false">mp</string>
        <string name="andcharge_callback_host" translatable="false">and-charge</string>

    In the manifest, your activity should have an intent filter with this data element
        <data android:scheme="@string/andcharge_callback_scheme"
        android:host="@string/andcharge_callback_host" />


How account linking works:

    1) Call your backend to initiate the account link
    2) Pass the result to &Charge by executing the CompleteAccountLinkCommand
    3) &Charge will try to complete the account linking
    4) &Charge will open this app with the callback url defined in your strings with extra params
    5) Use AndChargeCallbackUrlParser to convert Intent -> AccountLinkResult
    6) Show AccountLinkResult, for example by showing AccountLinkResultDialog

check for details: https://github.com/charge-partners/charge-and-partners/blob/master/link_partner_account.md

 *
 *
 * Author: romanvysotsky
 * Created: 06.07.20
 */

class SimpleMainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViews()
        showDeepLinkResultIfAvailable()
    }

    /*
    Check for AccountLinkResult in the intent and show AccountLinkResultDialog if result is available
     */
    private fun showDeepLinkResultIfAvailable() {

        val parser = AndChargeUrlParser.createInstance(this.applicationContext)
        val result = parser.parseCallbackUrl(intent)

        if(result != null) {
            AccountLinkResultDialog.createAndShow(supportFragmentManager, result)
        }
    }

    private fun setupViews() {
        val linkAccountBtn = findViewById<Button>(R.id.btn_link_account)
        linkAccountBtn.setOnClickListener { linkAccount() }
    }


    private fun linkAccount() {
        val response = initiateAccountLink()
        openAndChargeWithInitiateResponse(response)
    }

    /*
    assume this calls a backend to init the account link
     */
    private fun initiateAccountLink(): InitiateAccountLinkResponse {

        val partnerId = "PID001"
        return InitiateAccountLinkResponse(
            partnerId,
            "puid1",
            "code1",
            "INITIAL"
        )
    }

    /*
    handle the InitiateAccountLinkResponse by creating an accountLinkUrl and opening it
     */
    private fun openAndChargeWithInitiateResponse(response: InitiateAccountLinkResponse) {

        val parser = AndChargeUrlParser.createInstance(this.applicationContext)
        val accountLinkUrl = parser.createAccountLinkUrl(response)

        val command = OpenLinkCommand(accountLinkUrl)
        command.execute(this)
    }

}
