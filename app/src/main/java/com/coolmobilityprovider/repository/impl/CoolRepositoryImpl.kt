package com.coolmobilityprovider.repository.impl

import com.coolmobilityprovider.repository.CoolRepository
import com.r.andcharge.model.InitiateAccountLinkResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * represents your backend
 *
 * Author: romanvysotsky
 * Created: 03.07.20
 */

class CoolRepositoryImpl: CoolRepository {

    /*
     * Normally this should call your backend, and you can create an InitiateAccountLinkResponse.
     * You will also need those additional values:
     *
     * 1) PartnerId: Maybe it's a constant in your project, maybe your backend will deliver it on this call
     *
     * POST https://api.and-charge.com/v1/partners/{partnerId}/accounts?userId={UserId}
     * Accept: application/json
     * Content-Type: application/json
     * Authorization: Bearer <your JWT access token received during login>
     *
     * also check https://github.com/charge-partners/charge-and-partners/blob/master/link_partner_account.md#removing-an-account-link
     */

    override fun initiateAndChargeAccountLink(): Flow<InitiateAccountLinkResponse> = flow {

        val partnerId = "PID001"

        val response = InitiateAccountLinkResponse(
            partnerId,
            "puid1",
            "code1",
            "INITIAL"
        )

        emit(response)
    }

}
