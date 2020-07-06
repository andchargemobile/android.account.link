package com.r.andcharge.util

import android.content.Context
import android.content.Intent
import com.r.andcharge.R
import com.r.andcharge.model.AccountLinkResult

/**
 * Responsible for converting &Charge connect account callback urls
 * (as strings or from the starting intents) to an AccountLinkResult
 *
 * Author: romanvysotsky
 * Created: 03.07.20
 */

class AndChargeCallbackUrlParser(
    private val urlParser: UrlParser,
    private val callbackUrlScheme: String,
    private val callbackUrlHost: String
) {

    companion object {

        fun createInstance(context: Context): AndChargeCallbackUrlParser {
            val scheme = context.getString(R.string.andcharge_callback_scheme)
            val host = context.getString(R.string.andcharge_callback_host)
            val urlParser = UrlParser()
            return AndChargeCallbackUrlParser(urlParser, scheme, host)
        }

    }


    fun getAccountLinkResultOrNull(intent: Intent?): AccountLinkResult? {
        val intentData = intent?.data?.toString()
        return getAccountLinkResultOrNull(intentData)
    }

    fun getAccountLinkResultOrNull(intentData: String?): AccountLinkResult? {

        val callbackUrl = intentData ?: ""

        return if(isAndChargeConnectAccountCallbackUrl(callbackUrl)) {
            getAccountLinkResult(callbackUrl)
        } else {
            null
        }
    }

    private fun getAccountLinkResult(callbackUrl: String): AccountLinkResult {

        val paramOk = urlParser.getQueryParameter(url = callbackUrl, key = "ok")
            ?: return AccountLinkResult.MISSING_OK_PARAM

        return if(paramOk.toBoolean()) {
            AccountLinkResult.SUCCESS
        } else {
            getAccountLinkErrorResult(callbackUrl)
        }
    }


    private fun getAccountLinkErrorResult(callbackUrl: String): AccountLinkResult {

        val paramError = urlParser.getQueryParameter(url = callbackUrl, key = "error")

        return try {
            AccountLinkResult.valueOf(paramError ?: "")
        } catch (e: IllegalArgumentException) {
            AccountLinkResult.MISSING_ERROR_PARAM
        }
    }


    private fun isAndChargeConnectAccountCallbackUrl(callbackUrl: String): Boolean {
        return callbackUrl.contains(callbackUrlScheme) && callbackUrl.contains(callbackUrlHost)
    }

}
