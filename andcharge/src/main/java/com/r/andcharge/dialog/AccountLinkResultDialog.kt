package com.r.andcharge.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.r.andcharge.R
import com.r.andcharge.util.CallbackUrlParser

/**
 *
 * Author: romanvysotsky
 * Created: 03.07.20
 */

class AccountLinkResultDialog : DialogFragment() {

    companion object {
        private const val URL_KEY = "callbackUrl"
        private const val DIM_AMOUNT = 0.85f
    }

    private val callbackUrlParser: CallbackUrlParser = CallbackUrlParser()


    fun setCallbackUrl(callbackUrl: String) {
        val bundle = Bundle()
        bundle.putString(URL_KEY, callbackUrl)
        arguments = bundle
    }

    fun getCallbackUrl(): String {
        return arguments?.getString(URL_KEY) ?: ""
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.dialog_account_link_result, null)
        showResult(view)
        handleClickEvents(view)
        return view
    }

    override fun onStart() {
        super.onStart()
        showDialogFullScreenWithTransparentBackground()
    }


    private fun showDialogFullScreenWithTransparentBackground() {
        val dialog = dialog
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.WRAP_CONTENT

        dialog?.window?.setLayout(width, height)
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog?.window?.setDimAmount(DIM_AMOUNT)
    }


    private fun showResult(rootView: View) {
        val txtResult = rootView.findViewById<TextView>(R.id.txt_result)
        val callbackUrl = getCallbackUrl()
        val status = callbackUrlParser.getAccountLinkStatusFor(callbackUrl)

        txtResult.text = rootView.context.getString(status.textResourceId)
    }

    private fun handleClickEvents(rootView: View) {
        val imgClose = rootView.findViewById<ImageView>(R.id.img_close_icon)
        imgClose.setOnClickListener { dismiss() }
    }


}
