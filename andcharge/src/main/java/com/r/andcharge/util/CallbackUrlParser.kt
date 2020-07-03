package com.r.andcharge.util

import com.r.andcharge.model.AccountLinkStatus

/**
 * Responsible for converting callbackUrls returned by &Charge to an AccountLinkStatus
 *
 * Author: romanvysotsky
 * Created: 03.07.20
 */

class CallbackUrlParser(private val urlParser: UrlParser = UrlParser()) {


    fun getAccountLinkStatusFor(callbackUrl: String): AccountLinkStatus {

        val paramOk = urlParser.getQueryParameter(url = callbackUrl, key = "ok")
            ?: return AccountLinkStatus.MISSING_OK_PARAM

        return if(paramOk.toBoolean()) {
            AccountLinkStatus.SUCCESS
        } else {
            val paramError = urlParser.getQueryParameter(url = callbackUrl, key = "error")
            getAccountLinkErrorStatusFor(paramError)
        }
    }


    private fun getAccountLinkErrorStatusFor(paramError: String?): AccountLinkStatus {

        return try {
            AccountLinkStatus.valueOf(paramError ?: "")
        } catch (e: IllegalArgumentException) {
            AccountLinkStatus.MISSING_ERROR_PARAM
        }
    }

}
