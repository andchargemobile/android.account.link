package com.coolmobilityprovider.screens.main

import android.content.ActivityNotFoundException
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.coolmobilityprovider.R
import com.coolmobilityprovider.databinding.ActivityMainBinding
import com.r.andcharge.dialog.AccountLinkResultDialog


/**
 * Represents the screen where you link your user with &Charge.
 * Shows the usage in a sample mvvm project
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

 *
 * Author: romanvysotsky
 * Created: 03.07.20
 */

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels { MainViewModel.Factory(intent) }
    private var dataBinding: ActivityMainBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initDataBinding()

        listenToOnError()
        listenToAccountLinkComplete()
        listenToAccountLinkShowResult()
    }

    private fun initDataBinding() {
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        dataBinding?.viewModel = viewModel
        dataBinding?.lifecycleOwner = this
    }


    /*
     * Show the AccountLinkResult. For example by showing the AccountLinkResultDialog
     */
    private fun listenToAccountLinkShowResult() {

        viewModel.accountLinkResult.observe(this, Observer {
            if(it != null) AccountLinkResultDialog.createAndShow(supportFragmentManager, it)
        })
    }

    /*
     * Execute the command
     */
    private fun listenToAccountLinkComplete() {

        viewModel.accountLinkInitiated.observe(this, Observer {
            try {
                it?.execute(this)
            } catch (e: ActivityNotFoundException) {
                // watch out as the device might have neither &Charge nor a browser installed
                showError(e)
            }
        })
    }

    /*
     * Handle errors however
     */
    private fun listenToOnError() {
        viewModel.onError.observe(this, Observer {
            if(it != null) showError(it)
        })
    }


    private fun showError(error: Throwable) {
        val errorAsString = error.javaClass.simpleName + (error.message ?: "")
        val errorText = this.getString(R.string.main_error, errorAsString)
        showText(errorText)
    }

    private fun showText(text: String) {
        val toast = Toast.makeText(this, text, Toast.LENGTH_LONG)
        toast.show()
    }

}
