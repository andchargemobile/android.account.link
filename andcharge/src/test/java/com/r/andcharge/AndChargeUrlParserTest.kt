package com.r.andcharge

import android.content.Context
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.r.andcharge.model.AccountLinkResult
import com.r.andcharge.model.InitiateAccountLinkResponse
import com.r.andcharge.util.AndChargeUrlParser
import com.r.andcharge.util.UrlParser
import org.junit.Before
import org.junit.Test

/**
 *
 * Author: romanvysotsky
 * Created: 07.07.20
 */

class AndChargeUrlParserTest {

    lateinit var andChargeUrlParser: AndChargeUrlParser
    val context: Context = mock()
    val parser: UrlParser = mock()

    val scheme = "scheme"
    val host = "host"
    val path = "/path"


    @Before
    fun before() {

        whenever(context.getString(R.string.andcharge_callback_scheme)).thenReturn(scheme)
        whenever(context.getString(R.string.andcharge_callback_host)).thenReturn(host)
        whenever(context.getString(R.string.andcharge_callback_path)).thenReturn(path)

        andChargeUrlParser = AndChargeUrlParser(context, parser)
    }


    @Test
    fun createAccountLinkUrl_EncodesCallbackUrl_GetsStringWithParamsInExpectedOrder() {

        val expectedCallbackUrl = "$scheme://$host$path"
        val partnerId = "pid"
        val partnerUserId = "puid"
        val activationCode = "ac"
        val status = "st"
        val response = InitiateAccountLinkResponse(partnerId, partnerUserId, activationCode, status)
        val success  = "success"

        whenever(parser.encodeUtf8(expectedCallbackUrl)).thenReturn(expectedCallbackUrl)
        whenever(context.getString(
            R.string.url_andcharge_accountlink,
            response.activationCode,
            response.partnerId,
            response.partnerUserId,
            expectedCallbackUrl
        )).thenReturn(success)


        val result = andChargeUrlParser.createAccountLinkUrl(response)


        assert(result == success)
        verify(parser, times(1)).encodeUtf8(expectedCallbackUrl)
        verify(context, times(1)).getString(
            R.string.url_andcharge_accountlink,
            activationCode,
            partnerId,
            partnerUserId,
            expectedCallbackUrl
        )
    }


    @Test
    fun parseCallbackUrl_NullOrEmpty_ReturnNull() {

        val data1: String? = null
        val data2: String? = ""

        val result1 = andChargeUrlParser.parseCallbackUrl(data1)
        val result2 = andChargeUrlParser.parseCallbackUrl(data2)

        assert(result1 == null)
        assert(result2 == null)
    }


    @Test
    fun parseCallbackUrl_Ok_Success() {

        val callbackUrl = "$scheme://$host$path?ok=true"
        whenever(parser.getQueryParameter(callbackUrl, "ok")).thenReturn("true")

        val result = andChargeUrlParser.parseCallbackUrl(callbackUrl)

        assert(result == AccountLinkResult.SUCCESS)
    }

    @Test
    fun parseCallbackUrl_Ok_ErrorParamIsSet_StillSuccess() {

        val callbackUrl = "$scheme://$host$path?ok=true&error=PARTNER_NOT_FOUND"
        whenever(parser.getQueryParameter(callbackUrl, "ok")).thenReturn("true")

        val result = andChargeUrlParser.parseCallbackUrl(callbackUrl)

        assert(result == AccountLinkResult.SUCCESS)
    }

    @Test
    fun parseCallbackUrl_MessedUpCapitalization_Ok_Success() {

        val scheme = "sChEmE"
        val host = "hOsT"
        val path = "/pAtH"

        val callbackUrl = "$scheme://$host$path?ok=true"
        whenever(parser.getQueryParameter(callbackUrl, "ok")).thenReturn("true")

        val result = andChargeUrlParser.parseCallbackUrl(callbackUrl)

        assert(result == AccountLinkResult.SUCCESS)
    }


    @Test
    fun parseCallbackUrl_OkMissing_OkMissing() {

        val callbackUrl = "$scheme://$host$path?error=PARTNER_NOT_FOUND"
        whenever(parser.getQueryParameter(callbackUrl, "ok")).thenReturn(null)

        val result = andChargeUrlParser.parseCallbackUrl(callbackUrl)

        assert(result == AccountLinkResult.MISSING_OK_PARAM)
    }

    @Test
    fun parseCallbackUrl_NotOk_NoErrorParam_MissingErrorParam() {

        val callbackUrl = "$scheme://$host$path?ok=false"
        whenever(parser.getQueryParameter(callbackUrl, "ok")).thenReturn("false")
        whenever(parser.getQueryParameter(callbackUrl, "error")).thenReturn(null)

        val result = andChargeUrlParser.parseCallbackUrl(callbackUrl)

        assert(result == AccountLinkResult.MISSING_ERROR_PARAM)
    }

    @Test
    fun parseCallbackUrl_NotOk_UnknownErrorParam_MissingErrorParam() {

        val callbackUrl = "$scheme://$host$path?ok=false&error=someError"
        whenever(parser.getQueryParameter(callbackUrl, "ok")).thenReturn("false")
        whenever(parser.getQueryParameter(callbackUrl, "error")).thenReturn("someError")

        val result = andChargeUrlParser.parseCallbackUrl(callbackUrl)

        assert(result == AccountLinkResult.MISSING_ERROR_PARAM)
    }


    @Test
    fun parseCallbackUrl_NotOk_ErrorContainsError_ReturnEnum() {

        val callbackUrl = "$scheme://$host$path?ok=false&error=MANDATORY_PARAMETER_NOT_SET"
        whenever(parser.getQueryParameter(callbackUrl, "ok")).thenReturn("false")
        whenever(parser.getQueryParameter(callbackUrl, "error")).thenReturn("MANDATORY_PARAMETER_NOT_SET")

        val result = andChargeUrlParser.parseCallbackUrl(callbackUrl)

        assert(result == AccountLinkResult.MANDATORY_PARAMETER_NOT_SET)
    }

    @Test
    fun parseCallbackUrl_NotOk_ErrorContainsError_ReturnEnum_CaseInsensitive() {

        val callbackUrl = "$scheme://$host$path?oK=false&eRror=MAndatOrY_PArameTER_NoT_sEt"
        whenever(parser.getQueryParameter(callbackUrl, "ok")).thenReturn("false")
        whenever(parser.getQueryParameter(callbackUrl, "error")).thenReturn("MAndatOrY_PArameTER_NoT_sEt")

        val result = andChargeUrlParser.parseCallbackUrl(callbackUrl)

        assert(result == AccountLinkResult.MANDATORY_PARAMETER_NOT_SET)
    }


    @Test
    fun parseCallbackUrl_NotOk_ErrorContainsSuccess_MissingErrorParam() {

        val callbackUrl = "$scheme://$host$path?ok=false&error=SUCCESS"
        whenever(parser.getQueryParameter(callbackUrl, "ok")).thenReturn("false")
        whenever(parser.getQueryParameter(callbackUrl, "error")).thenReturn("SUCCESS")

        val result = andChargeUrlParser.parseCallbackUrl(callbackUrl)

        assert(result == AccountLinkResult.MISSING_ERROR_PARAM)
    }

    @Test
    fun parseCallbackUrl_SomeUnknownUrl_ReturnNull() {

        val callbackUrl1 = "https://www.google.com?ok=false&error=MANDATORY_PARAMETER_NOT_SET"
        val callbackUrl2 = "https://www.google.com?ok=true"
        val callbackUrl3 = "https://www.google.com?error=MANDATORY_PARAMETER_NOT_SET"
        val callbackUrl4 = "https://www.google.com"

        val result1 = andChargeUrlParser.parseCallbackUrl(callbackUrl1)
        val result2 = andChargeUrlParser.parseCallbackUrl(callbackUrl2)
        val result3 = andChargeUrlParser.parseCallbackUrl(callbackUrl3)
        val result4 = andChargeUrlParser.parseCallbackUrl(callbackUrl4)

        assert(result1 == null)
        assert(result2 == null)
        assert(result3 == null)
        assert(result4 == null)
    }

    @Test
    fun parseCallbackUrl_SomeUnknownUrl_ContainsHostAndSchemeAndOkParamByChance_ReturnNull() {

        val callbackUrl = "https://www.google.com?ok=true&foo=$scheme&bar=$host"

        val result = andChargeUrlParser.parseCallbackUrl(callbackUrl)

        assert(result == null)
    }

    @Test
    fun parseCallbackUrl_MalformedUrl_ReturnNull() {

        val callbackUrl = "$host://$path?=$scheme?ok=true&foo=$scheme&bar=$host"

        val result = andChargeUrlParser.parseCallbackUrl(callbackUrl)

        assert(result == null)
    }


}
