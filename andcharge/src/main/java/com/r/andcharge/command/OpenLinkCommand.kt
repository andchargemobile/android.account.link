package com.r.andcharge.command

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri

/**
 * Opens a given link per with an ACTION_VIEW intent
 *
 * Author: romanvysotsky
 * Created: 03.07.20
 */

class OpenLinkCommand(private val link: String) : Command {

    @Throws(ActivityNotFoundException::class)
    override fun execute(context: Context) {

        val intentUri = Uri.parse(link)
        val intent = Intent(Intent.ACTION_VIEW, intentUri)
        context.startActivity(intent)

    }

}
