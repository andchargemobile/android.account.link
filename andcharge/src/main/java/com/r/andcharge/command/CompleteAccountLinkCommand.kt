package com.r.andcharge.command

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.r.andcharge.R
import com.r.andcharge.model.InitiateAccountLinkResponse
import java.net.URLEncoder

/**
 * Opens &Charge app or browser with the necessary parameters to complete the initiated account link
 *
 * response = Contains values which your backend should provide after initiating an account link
 *
 * Author: romanvysotsky
 * Created: 03.07.20
 */

class CompleteAccountLinkCommand(private val response: InitiateAccountLinkResponse) : Command {


    @Throws(ActivityNotFoundException::class)
    override fun execute(context: Context) {

        val andChargeDeepLink = getAndChargeDeepLink(context)
        val intentUri = Uri.parse(andChargeDeepLink)
        val intent = Intent(Intent.ACTION_VIEW, intentUri)

        context.startActivity(intent)
    }


    private fun getAndChargeDeepLink(context: Context): String {
        return context.getString(
            R.string.url_andcharge_accountlink,
            response.activationCode,
            response.partnerId,
            response.partnerUserId,
            createCallbackUrl(context)
        )
    }

    private fun createCallbackUrl(context: Context): String {
        val scheme = context.getString(R.string.andcharge_callback_scheme)
        val host = context.getString(R.string.andcharge_callback_host)
        val callbackUrl = "$scheme://$host"

        return URLEncoder.encode(callbackUrl, "UTF-8")
    }

}
