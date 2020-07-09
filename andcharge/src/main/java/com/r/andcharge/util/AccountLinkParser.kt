package com.r.andcharge.util

import android.content.Context
import android.content.Intent
import com.r.andcharge.model.AccountLinkInit
import com.r.andcharge.model.AccountLinkResult

/**
 *
 * Author: romanvysotsky
 * Created: 09.07.20
 */

interface AccountLinkParser {

    companion object {
        fun createInstance(context: Context, urlParser: UrlParser): AccountLinkParser {
            return AccountLinkParserImpl(context, urlParser)
        }
    }


    fun parseAccountLinkInit(response: AccountLinkInit): String

    fun parseAccountLinkResult(intent: Intent?): AccountLinkResult?
    fun parseAccountLinkResult(intentData: String?): AccountLinkResult?


}
