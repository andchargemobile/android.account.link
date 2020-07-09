package com.r.andcharge.util

import android.content.Context
import android.content.Intent
import com.r.andcharge.R
import com.r.andcharge.model.AccountLinkInit
import com.r.andcharge.model.AccountLinkResult

/**
 * Default impl of [AccountLinkParser], check it for more details
 *
 * AccountLinkInit -> &Charge deep link
 * &Charge callback -> AccountLinkResult
 *
 * Author: romanvysotsky
 * Created: 03.07.20
 */

class AccountLinkParserImpl(
    private val context: Context,
    private val urlParser: UrlParser
): AccountLinkParser {


    private val scheme = context.getString(R.string.andcharge_callback_scheme)
    private val host = context.getString(R.string.andcharge_callback_host)
    private val path = context.getString(R.string.andcharge_callback_path)
    private val callbackUrl = "$scheme://$host$path"



    override fun parseAccountLinkInit(response: AccountLinkInit): String {
        val callbackUrl = urlParser.encodeUtf8(this.callbackUrl)
        return context.getString(
            R.string.url_andcharge_accountlink,
            response.activationCode,
            response.partnerId,
            response.partnerUserId,
            callbackUrl
        )
    }


    override fun parseAccountLinkResult(intent: Intent?): AccountLinkResult? {
        val intentData = intent?.data?.toString()
        return parseAccountLinkResult(intentData)
    }


    override fun parseAccountLinkResult(intentData: String?): AccountLinkResult? {

        val callbackUrl = intentData ?: ""

        return if(isAndChargeConnectAccountCallbackUrl(callbackUrl)) {
            parseAccountLink(callbackUrl)
        } else {
            null
        }
    }


    private fun isAndChargeConnectAccountCallbackUrl(callbackUrl: String): Boolean {
        return callbackUrl.startsWith(this.callbackUrl, true)
    }


    private fun parseAccountLink(callbackUrl: String): AccountLinkResult {

        val paramOk = urlParser.getQueryParameter(url = callbackUrl, key = "ok")
            ?: return AccountLinkResult.MISSING_OK_PARAM

        return if(paramOk.toBoolean()) {
            AccountLinkResult.SUCCESS
        } else {
            parseAccountLinkError(callbackUrl)
        }
    }

    private fun parseAccountLinkError(callbackUrl: String): AccountLinkResult {

        val paramError = urlParser.getQueryParameter(url = callbackUrl, key = "error")

        return try {
            AccountLinkResult.errorValueOf(paramError)
        } catch (e: IllegalArgumentException) {
            AccountLinkResult.MISSING_ERROR_PARAM
        }
    }

}
