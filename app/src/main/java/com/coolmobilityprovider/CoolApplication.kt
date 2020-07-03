package com.coolmobilityprovider

import android.app.Application
import com.coolmobilityprovider.di.externalModule
import com.coolmobilityprovider.di.localModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 *
 * Author: romanvysotsky
 * Created: 03.07.20
 */

class CoolApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initDi()
    }

    private fun initDi() {
        startKoin {
            androidContext(this@CoolApplication)
            androidLogger()
            modules(listOf(externalModule, localModule))
        }
    }

}
