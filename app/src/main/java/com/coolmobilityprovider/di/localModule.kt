package com.coolmobilityprovider.di

import com.coolmobilityprovider.repository.LocalRepository
import com.coolmobilityprovider.repository.impl.LocalRepositoryImpl
import com.r.andcharge.util.AndChargeCallbackUrlParser
import org.koin.dsl.module

/**
 *
 * Author: romanvysotsky
 * Created: 03.07.20
 */

val localModule = module {

    single<LocalRepository> { LocalRepositoryImpl(get()) }

    single { AndChargeCallbackUrlParser.createInstance(get()) }

}
