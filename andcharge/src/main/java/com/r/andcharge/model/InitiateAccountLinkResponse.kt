package com.r.andcharge.model

/**
 * partnerId      = Your partner ID with which you registered yourself as a partner
 * partnerUserId  = The user for which you request the account link (user ID is defined by you, we'll map this to one of our users subsequently).
 * activationCode = The activation code that needs to be passed to the &Charge webpage or app to complete the link
 * status         = An enumeration reporting on the status of the operation. Possible values are: INITIAL, LINKED
 *
 * Author: romanvysotsky
 * Created: 03.07.20
 */

data class InitiateAccountLinkResponse(
    val partnerId: String,
    val partnerUserId: String,
    val activationCode: String,
    val status: String
)
