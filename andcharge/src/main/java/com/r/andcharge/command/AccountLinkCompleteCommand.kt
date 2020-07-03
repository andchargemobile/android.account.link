package com.r.andcharge.command

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import com.r.andcharge.R
import com.r.andcharge.model.InitiateAccountLinkResponse
import java.net.URLEncoder

/**
 * Opens &Charge app or browser with the necessary parameters to complete the initiated account link
 *
 * response = Contains values which your backend should provide when initiating an account link
 * callbackUrl = &Charge will try to deep link into your app with the url you provide for this property
 *
 * Author: romanvysotsky
 * Created: 03.07.20
 */

class AccountLinkCompleteCommand(
    private val response: InitiateAccountLinkResponse
) : Command {


    @Throws(ActivityNotFoundException::class)
    override fun execute(context: AppCompatActivity) {

        val andChargeDeepLink = getAndChargeDeepLink(context)
        val intentUri = Uri.parse(andChargeDeepLink)
        val intent = Intent(Intent.ACTION_VIEW, intentUri)
        context.startActivity(intent)
    }


    private fun getAndChargeDeepLink(context: Context): String {

        val callbackUrl = URLEncoder.encode(response.callbackUrl, "UTF-8")

        return context.getString(
            R.string.url_andcharge_accountlink,
            response.activationCode,
            response.partnerId,
            response.partnerUserId,
            callbackUrl
        )
    }

}
