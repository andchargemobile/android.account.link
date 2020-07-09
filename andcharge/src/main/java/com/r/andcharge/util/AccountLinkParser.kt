package com.r.andcharge.util

import android.content.Context
import android.content.Intent
import com.r.andcharge.model.AccountLinkInit
import com.r.andcharge.model.AccountLinkResult

/**
 * Responsible for parsing:
 *
 * [AccountLinkInit] -> &Charge deep link
 * Callback url from &Charge -> [AccountLinkResult]
 *
 * Author: romanvysotsky
 * Created: 09.07.20
 */

interface AccountLinkParser {


    companion object {

        /**
         * Creates a default instance of [AccountLinkParser]
         *
         * @param context application context
         * @param urlParser util
         * @return default instance of [AccountLinkParser]
         */
        fun createInstance(context: Context, urlParser: UrlParser): AccountLinkParser {
            return AccountLinkParserImpl(context, urlParser)
        }
    }


    /**
     * Parses [AccountLinkInit] to an &Charge deep link
     *
     * @param response what you got back after initiating an account link
     * @return a deep link into &Charge, example: https://and-charge.com/#/confirmAccountLink?activationCode=code1&partnerId=PID001&partnerUserId=pid1&callbackUrl=https%3A%2F%2Fcom.mobility.provider%2Fand-charge%2Flink
     */
    fun parseAccountLinkInit(response: AccountLinkInit): String

    /**
     * Parses [Intent] to [AccountLinkResult]
     *
     * @param intent the intent an activity was started with or a new intent from onNewIntent
     * @return [AccountLinkResult] if the [intent] contained a valid callback url, null otherwise
     */
    fun parseAccountLinkResult(intent: Intent?): AccountLinkResult?

    /**
     * Parses [String] to [AccountLinkResult]
     *
     * @param intentData callback url or any nullable string
     * @return [AccountLinkResult] if the [intentData] is a valid callback url, null otherwise
     */
    fun parseAccountLinkResult(intentData: String?): AccountLinkResult?


}
