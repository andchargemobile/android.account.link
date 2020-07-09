package com.r.andcharge

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.r.andcharge.model.AccountLinkInit
import com.r.andcharge.model.AccountLinkResult
import com.r.andcharge.util.AccountLinkParser
import com.r.andcharge.view.AccountLinkViewModel
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 *
 * Author: romanvysotsky
 * Created: 09.07.20
 */

class AccountLinkViewModelTest {

    @get:Rule val rule = InstantTaskExecutorRule()

    lateinit var viewModel: AccountLinkViewModel

    val accountLinkParser: AccountLinkParser = mock()

    @Before
    fun before() {
        viewModel = AccountLinkViewModel(accountLinkParser)
    }


    @Test
    fun onInitAccountLink_CallParser_CreateCommandWithDeepLink() {

        val partnerId = "pid"
        val partnerUserId = "puid"
        val activationCode = "ac"
        val status = "st"
        val response = AccountLinkInit(partnerId, partnerUserId, activationCode, status)
        val deepLinkAndCharge = "foo://bar"

        whenever(accountLinkParser.parseAccountLinkInit(response)).thenReturn(deepLinkAndCharge)

        viewModel.onInitAccountLink(response)

        assertEquals(deepLinkAndCharge, viewModel.accountLinkInitCommand.value?.link)
        verify(accountLinkParser, times(1)).parseAccountLinkInit(response)
    }


    @Test
    fun onIntentData_CallParser_CreateCommandWithResult() {

        val expected = AccountLinkResult.SUCCESS
        val callbackUrlFromAndCharge = "foo://bar"
        whenever(accountLinkParser.parseAccountLinkResult(callbackUrlFromAndCharge)).thenReturn(expected)

        viewModel.onIntentData(callbackUrlFromAndCharge)

        assertEquals(expected, viewModel.accountLinkResultCommand.value?.result)
        verify(accountLinkParser, times(1)).parseAccountLinkResult(callbackUrlFromAndCharge)
    }

}
