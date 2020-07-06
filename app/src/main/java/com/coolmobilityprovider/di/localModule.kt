package com.coolmobilityprovider.di

import com.r.andcharge.util.AndChargeCallbackUrlParser
import org.koin.dsl.module

/**
 *
 * Author: romanvysotsky
 * Created: 03.07.20
 */

val localModule = module {

    single { AndChargeCallbackUrlParser.createInstance(get()) }

}
