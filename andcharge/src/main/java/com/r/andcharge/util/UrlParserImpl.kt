package com.r.andcharge.util

import android.net.Uri
import java.net.URLEncoder

/**
 * Default impl of [UrlParser]
 *
 * Author: romanvysotsky
 * Created: 09.07.20
 */

class UrlParserImpl: UrlParser {

    override fun getQueryParameter(url: String, key: String): String? {
        val uri = Uri.parse(url)
        return uri.getQueryParameter(key)
    }

    override fun encodeUtf8(url: String): String {
        return URLEncoder.encode(url, "UTF-8")
    }

}
