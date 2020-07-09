package com.r.andcharge.util

/**
 * Wraps url utility methods
 *
 * Author: romanvysotsky
 * Created: 03.07.20
 */

interface UrlParser {

    companion object {
        fun createInstance(): UrlParser {
            return UrlParserImpl()
        }
    }

    fun getQueryParameter(url: String, key: String): String?
    fun encodeUtf8(url: String): String

}
