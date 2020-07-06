package com.r.andcharge.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.r.andcharge.R
import com.r.andcharge.model.AccountLinkResult

/**
 * A simple dialog showing the &Charge logo and the text from AccountLinkResult
 *
 * Author: romanvysotsky
 * Created: 03.07.20
 */

class AccountLinkResultDialog : DialogFragment() {


    companion object {
        private const val RESULT_KEY = "AccountLinkResult"
        private const val DIM_AMOUNT = 0.85f

        fun createAndShow(fragmentManager: FragmentManager, result: AccountLinkResult) {
            val accountLinkResultDialog = AccountLinkResultDialog()
            accountLinkResultDialog.setAccountLinkResult(result)
            accountLinkResultDialog.show(fragmentManager, AccountLinkResultDialog::class.java.name)
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.dialog_account_link_result, container)
        showAccountLinkResult(view)
        showCloseButton(view)

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


    private fun showAccountLinkResult(rootView: View) {
        val result = getAccountLinkResult()
        if(result == null) {
            dismiss()
            return
        }

        val txtResult = rootView.findViewById<TextView>(R.id.txt_result)
        txtResult.text = rootView.context.getString(result.textResourceId)
    }

    private fun showCloseButton(rootView: View) {
        val imgClose = rootView.findViewById<ImageView>(R.id.img_close_icon)
        imgClose.setOnClickListener { dismiss() }
    }



    private fun setAccountLinkResult(result: AccountLinkResult) {
        val bundle = Bundle()
        bundle.putSerializable(RESULT_KEY, result)
        arguments = bundle
    }

    private fun getAccountLinkResult(): AccountLinkResult? {
        val result = arguments?.getSerializable(RESULT_KEY)
        return if(result is AccountLinkResult) result else null
    }

}
