package com.coolmobilityprovider.di

import com.coolmobilityprovider.repository.CoolRepository
import com.coolmobilityprovider.repository.impl.CoolRepositoryImpl
import org.koin.dsl.module

/**
 *
 * Author: romanvysotsky
 * Created: 03.07.20
 */

val externalModule = module {

    single<CoolRepository> { CoolRepositoryImpl() }

}
