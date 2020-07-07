package com.r.andcharge.util

import android.content.Context
import android.content.Intent
import com.r.andcharge.R
import com.r.andcharge.model.AccountLinkResult
import com.r.andcharge.model.InitiateAccountLinkResponse

/**
 * Responsible for urls regarding &Charge
 *
 * InitiateAccountLinkResponse -> Url to open &Charge and complete the account link
 * Callback url -> AccountLinkResponse
 *
 * Author: romanvysotsky
 * Created: 03.07.20
 */

class AndChargeUrlParser(
    private val urlParser: UrlParser,
    private val context: Context
) {

    companion object {

        fun createInstance(context: Context): AndChargeUrlParser {
            val urlParser = UrlParser()
            return AndChargeUrlParser(urlParser, context)
        }

    }

    private val scheme = context.getString(R.string.andcharge_callback_scheme)
    private val host = context.getString(R.string.andcharge_callback_host)


    /*
    returns a url string
    which tells &Charge to complete an account link for the given parameters
    example: https://and-charge.com/#/confirmAccountLink?activationCode=code1&partnerId=PID001&partnerUserId=pid1&callbackUrl=https%3A%2F%2Fcom.mobility.provider%2Fand-charge%2Flink
    */
    fun createAccountLinkUrl(response: InitiateAccountLinkResponse): String {

        val callbackUrl = urlParser.encodeUtf8("$scheme://$host")

        return context.getString(
            R.string.url_andcharge_accountlink,
            response.activationCode,
            response.partnerId,
            response.partnerUserId,
            callbackUrl
        )
    }


    /*
    returns the AccountLinkResult
    if the intent data contains a valid callback url from &Charge for account linking
    or null otherwise
     */
    fun parseCallbackUrl(intent: Intent?): AccountLinkResult? {
        val intentData = intent?.data?.toString()
        return parseCallbackUrl(intentData)
    }

    /*
    returns the AccountLinkResult
    if the given string is a valid callback url from &Charge for account linking
    or null otherwise
     */
    fun parseCallbackUrl(intentData: String?): AccountLinkResult? {

        val callbackUrl = intentData ?: ""

        return if(isAndChargeConnectAccountCallbackUrl(callbackUrl)) {
            parseAccountLinkResult(callbackUrl)
        } else {
            null
        }
    }


    private fun isAndChargeConnectAccountCallbackUrl(callbackUrl: String): Boolean {
        return callbackUrl.contains(scheme) && callbackUrl.contains(host)
    }

    private fun parseAccountLinkResult(callbackUrl: String): AccountLinkResult {

        val paramOk = urlParser.getQueryParameter(url = callbackUrl, key = "ok")
            ?: return AccountLinkResult.MISSING_OK_PARAM

        return if(paramOk.toBoolean()) {
            AccountLinkResult.SUCCESS
        } else {
            parseAccountLinkResultError(callbackUrl)
        }
    }

    private fun parseAccountLinkResultError(callbackUrl: String): AccountLinkResult {

        val paramError = urlParser.getQueryParameter(url = callbackUrl, key = "error")

        return try {
            AccountLinkResult.valueOf(paramError ?: "")
        } catch (e: IllegalArgumentException) {
            AccountLinkResult.MISSING_ERROR_PARAM
        }
    }

}
