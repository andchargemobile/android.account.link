package com.coolmobilityprovider.repository

import com.r.andcharge.model.InitiateAccountLinkResponse

/**
 *
 * Author: romanvysotsky
 * Created: 07.07.20
 */

fun LocalRepository.createInitiateAccountLinkResponseFromLocalValues(): InitiateAccountLinkResponse {

    val partnerId = retrieve("partnerId") ?: "partnerId"
    val partnerUserId = retrieve("partnerUserId") ?: "partnerUserId"
    val activationCode = retrieve("activationCode") ?: "activationCode"
    val status = retrieve("status") ?: "status"

    return InitiateAccountLinkResponse(
        partnerId,
        partnerUserId,
        activationCode,
        status
    )
}
