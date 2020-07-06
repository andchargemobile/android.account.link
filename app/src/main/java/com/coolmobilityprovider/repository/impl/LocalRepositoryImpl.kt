package com.coolmobilityprovider.repository.impl

import android.content.Context
import androidx.core.content.edit
import com.coolmobilityprovider.repository.LocalRepository

/**
 * just a shared prefs abstraction
 *
 * Author: romanvysotsky
 * Created: 06.07.20
 */

class LocalRepositoryImpl(private val context: Context) : LocalRepository {

    companion object {
        private const val KEY = "coolMpPrefs"
    }

    override fun save(key: String, value: String) {
        val prefs = context.getSharedPreferences(KEY, Context.MODE_PRIVATE)
        prefs.edit(commit = true) {
            putString(key, value)
        }
    }

    override fun retrieve(key: String): String? {
        val prefs = context.getSharedPreferences(KEY, Context.MODE_PRIVATE)
        return prefs.getString(key, null)
    }

}
