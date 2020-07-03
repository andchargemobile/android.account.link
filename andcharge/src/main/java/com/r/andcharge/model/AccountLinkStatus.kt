package com.r.andcharge.model

import com.r.andcharge.R

/**
 * enum representing all states which can occur when completing an initiated account link
 *
 * Author: romanvysotsky
 * Created: 03.07.20
 */

enum class AccountLinkStatus(val textResourceId: Int) {

    SUCCESS(R.string.connect_partner_result_success),
    MISSING_OK_PARAM(R.string.connect_partner_result_missing_ok),
    MISSING_ERROR_PARAM(R.string.connect_partner_result_missing_error),

    MANDATORY_PARAMETER_NOT_SET(R.string.connect_partner_result_param_not_set),
    ACCOUNT_LINKED_TO_DIFFERENT_USER(R.string.connect_partner_result_linked_to_different),
    PARTNER_NOT_FOUND(R.string.connect_partner_result_partner_not_found),
    GENERAL_PROCESSING_FAILURE(R.string.connect_partner_result_general_processing),
    AUTHORIZATION_INVALID(R.string.connect_partner_result_authorization_invalid),
    ACTIVATION_CODE_NOT_FOUND(R.string.connect_partner_result_activation_code_not_found),
    REFERENCED_OBJECT_NOT_FOUND(R.string.connect_partner_result_param_not_set),

}
