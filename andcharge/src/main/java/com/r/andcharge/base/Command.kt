package com.r.andcharge.base

import androidx.fragment.app.FragmentActivity

/**
 *
 * Author: romanvysotsky
 * Created: 03.07.20
 */

interface Command {

    fun execute(context: FragmentActivity)

}
