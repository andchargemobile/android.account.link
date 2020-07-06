package com.coolmobilityprovider.repository

/**
 * just a shared prefs abstraction
 *
 * Author: romanvysotsky
 * Created: 06.07.20
 */

interface LocalRepository {

    fun save(key: String, value: String)
    fun retrieve(key: String): String?

}
