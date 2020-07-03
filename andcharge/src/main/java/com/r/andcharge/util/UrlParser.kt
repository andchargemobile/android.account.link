package com.r.andcharge.util

import android.net.Uri

/**
 *
 * Author: romanvysotsky
 * Created: 03.07.20
 */

class UrlParser {

    fun getQueryParameter(url: String, key: String): String? {
        val uri = Uri.parse(url)
        return uri.getQueryParameter(key)
    }

}
