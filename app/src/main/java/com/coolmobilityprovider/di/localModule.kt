package com.coolmobilityprovider.di

import com.coolmobilityprovider.repository.LocalRepository
import com.coolmobilityprovider.repository.impl.LocalRepositoryImpl
import com.r.andcharge.util.AccountLinkParser
import com.r.andcharge.util.UrlParser
import org.koin.dsl.module

/**
 * This is just for showing the url &Charge would be called with;
 * you will not have to use any of these
 *
 * Author: romanvysotsky
 * Created: 03.07.20
 */

val localModule = module {

    single<LocalRepository> { LocalRepositoryImpl(get()) }
    single { AccountLinkParser(get(), get()) }
    single { UrlParser.createInstance() }

}
