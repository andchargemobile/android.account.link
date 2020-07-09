package com.r.andcharge.util

/**
 * Abstraction for url utility methods
 *
 * Author: romanvysotsky
 * Created: 03.07.20
 */

interface UrlParser {


    companion object {
        /**
         * Creates a default UrlParser
         * @return default [UrlParser]
         */
        fun createInstance(): UrlParser {
            return UrlParserImpl()
        }
    }


    /**
     * Returns the query parameter value from an [url] associated with the given [key]
     *
     * @param url any url
     * @param key query parameter key
     *
     * @return the value associated with the query param key, null if there was no such query param or if the url was invalid
     */
    fun getQueryParameter(url: String, key: String): String?

    /**
     * Url-encodes any string
     *
     * @param url any string
     * @return url encoded string
     */
    fun encodeUtf8(url: String): String

}
