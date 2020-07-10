package com.r.andcharge.command

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.r.andcharge.BuildConfig
import com.r.andcharge.R
import com.r.andcharge.base.Command

/**
 * Opens preferably &Charge or a browser with the [link]
 *
 * @property link a deep link for &Charge
 *
 * Author: romanvysotsky
 * Created: 07.07.20
 */

class OpenAccountLinkInitCommand(val link: String) : Command {


    override fun execute(context: FragmentActivity) {

        val intentUri = Uri.parse(link)
        val intentAction = Intent.ACTION_VIEW

        val intent = getAndChargeLaunchIntent(context)
        intent.action = intentAction
        intent.data = intentUri
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP

        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            val cannotLinkAccount = context.getString(R.string.connect_cannot_link)
            Toast.makeText(context, cannotLinkAccount, Toast.LENGTH_LONG).show()
        }
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
