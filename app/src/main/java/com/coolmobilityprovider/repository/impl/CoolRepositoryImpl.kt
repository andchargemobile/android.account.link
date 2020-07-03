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
     * PartnerId: Maybe it's a constant in your project, maybe your backend will deliver it on this call
     * CallbackUrl: As &Charge will try to deep link into your app, this should be constant for the project
     *              and you should define an intent filter for it in the manifest.
     *              Example: coolmp://and-charge-callback
     *
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
        val callbackUrl = "coolmp://and-charge-callback"

        val response = InitiateAccountLinkResponse(
            partnerId,
            callbackUrl,
            "puid1",
            "code1",
            "INITIAL"
        )

        emit(response)
    }

}
