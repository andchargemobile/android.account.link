package com.coolmobilityprovider.repository

import com.r.andcharge.model.InitiateAccountLinkResponse
import kotlinx.coroutines.flow.Flow

/**
 * represents your backend
 *
 * Author: romanvysotsky
 * Created: 03.07.20
 */

interface CoolRepository {

    fun initiateAndChargeAccountLink(): Flow<InitiateAccountLinkResponse>

}
