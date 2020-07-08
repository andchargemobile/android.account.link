package com.r.andcharge.util

import android.net.Uri
import java.net.URLEncoder

/**
 * Wraps url utility methods
 *
 * Author: romanvysotsky
 * Created: 03.07.20
 */

interface UrlParser {

    companion object {
        fun createInstance(): UrlParser {
            return AndroidUrlParser()
        }
    }


    fun getQueryParameter(url: String, key: String): String?
    fun encodeUtf8(url: String): String


    class AndroidUrlParser: UrlParser {

        override fun getQueryParameter(url: String, key: String): String? {
            val uri = Uri.parse(url)
            return uri.getQueryParameter(key)
        }

        override fun encodeUtf8(url: String): String {
            return URLEncoder.encode(url, "UTF-8")
        }

    }

}
