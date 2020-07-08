package com.coolmobilityprovider.screens.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.coolmobilityprovider.R
import com.coolmobilityprovider.base.showError
import com.coolmobilityprovider.databinding.ActivityMainBinding
import com.r.andcharge.view.AccountLinkView


/**
 * Represents the screen where you link your user with &Charge.
 * Shows the usage in a sample mvvm project
 *
 *

How to set up:

    redefine the callback url scheme & host strings in your ids.xml or strings.xml to fit your requirements
        <string name="andcharge_callback_scheme" translatable="false">mp</string>
        <string name="andcharge_callback_host" translatable="false">and-charge</string>
        <string name="andcharge_callback_path" translatable="false" />

    In the manifest, your activity should have an intent filter with this data element
        <data android:scheme="@string/andcharge_callback_scheme"
        android:host="@string/andcharge_callback_host" />


How to use in the project:

    1) OnCreate of the activity receiving the callback url intent, create AccountLinkView:
        val view = AccountLinkView(this)
        view.showAccountLinkResult(intent)
        view.showAccountLinkInit(viewModel.onAccountLinkInitiated)

    2) If you are using android:launchMode="singleTask", override onNewIntent
       and pass the new intent there as well: view.onAccountLinkResultReceived(intent)

    3) Call your backend to and post successful results to viewModel.onAccountLinkInitiated
       alternatively pass the result manually when received view.showAccountLinkInit(result)


The account linking flow:

    1) Your backend initiates an account link and the result AccountLinkInit is passed to AccountLinkView
    2) The sdk parses AccountLinkInit to a deep link for &Charge to handle
    3) The sdk deep links into &Charge or opens a browser
    4) &Charge completes the account link, it can be successful or some error occurs
    5) &Charge deep links into your app with the url you defined in "How to set up" with extra params
    6) You pass the intent to AccountLinkView
    7) AccountLinkView parses: Intent -> AccountLinkResult
    8) A fragment dialog is shown with the result

check for details on the data types etc:
https://github.com/charge-partners/charge-and-partners/blob/master/link_partner_account.md

 *
 * Author: romanvysotsky
 * Created: 03.07.20
 */

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private var dataBinding: ActivityMainBinding? = null
    private var accountLinkView: AccountLinkView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        listenToOnErrors()

        initDataBinding()
        initAccountLinkView()
    }


    private fun listenToOnErrors() {
        viewModel.onError.observe(this, Observer {
            if (it != null) showError(it)
        })
    }


    private fun initDataBinding() {
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        dataBinding?.viewModel = viewModel
        dataBinding?.lifecycleOwner = this
    }


    /*
     * Instantiate an AccountLinkView and make it subscribe to initiated account links
     */
    private fun initAccountLinkView() {

        val view = AccountLinkView(this)
        view.showAccountLinkResult(intent)
        view.showAccountLinkInit(viewModel.onAccountLinkInitiated)
        accountLinkView = view

    }


    /*
     * If you're using android:launchMode="singleTask", also check the new intent for results
     */
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        accountLinkView?.showAccountLinkResult(intent)
    }

}
