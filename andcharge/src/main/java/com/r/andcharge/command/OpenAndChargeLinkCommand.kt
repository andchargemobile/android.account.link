package com.r.andcharge.command

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.r.andcharge.BuildConfig

/**
 * opens &Charge directly if its installed with the given link
 * if its not installed, open any app that can handle the link
 *
 * Author: romanvysotsky
 * Created: 07.07.20
 */

class OpenAndChargeLinkCommand(private val link: String) : Command {


    @Throws(ActivityNotFoundException::class)
    override fun execute(context: Context) {

        val intentUri = Uri.parse(link)
        val intentAction = Intent.ACTION_VIEW

        val intent = getAndChargeLaunchIntent(context)
        intent.action = intentAction
        intent.data = intentUri
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP

        context.startActivity(intent)
    }


    private fun getAndChargeLaunchIntent(context: Context): Intent {

        val intent = if(BuildConfig.DEBUG) {
            context
                .launchIntent("com.andcharge.mobile.debug") ?: context
                .launchIntent("com.andcharge.debug")
        } else {
            context
                .launchIntent("com.andcharge")
        }

        return intent ?: Intent()
    }

    private fun Context.launchIntent(packageName: String): Intent? {
        return packageManager.getLaunchIntentForPackage(packageName)
    }

}
