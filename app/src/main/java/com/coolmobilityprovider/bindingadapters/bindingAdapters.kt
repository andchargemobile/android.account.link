package com.coolmobilityprovider.bindingadapters

import android.text.Editable
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.core.widget.addTextChangedListener
import androidx.databinding.BindingAdapter
import com.coolmobilityprovider.base.getKoinInstance
import com.coolmobilityprovider.repository.LocalRepository

/**
 *
 * Author: romanvysotsky
 * Created: 06.07.20
 */

interface TextListener {
    fun onChanged(text: String)
}

@BindingAdapter("bindTextChanged")
fun bindTextChanged(editText: EditText, listener: TextListener?) {
    if(listener == null) {
        return
    }

    editText.addTextChangedListener(afterTextChanged = {
        val value = it?.toString() ?: return@addTextChangedListener
        listener.onChanged(value)
    })
}

/*
just a quick utility binding for syncing text with LocalRepository
 */
@BindingAdapter("bindTextToLocalRepositoryKey")
fun bindTextToLocalRepositoryKey(editText: EditText, key: String?) {
    if(key == null) {
        return
    }

    val localRepository = getKoinInstance<LocalRepository>()
    val text = localRepository.retrieve(key) ?: key
    val editable = Editable.Factory.getInstance().newEditable(text)
    editText.text = editable

    editText.addTextChangedListener(afterTextChanged = {
        val value = it?.toString() ?: key
        localRepository.save(key, value)
    })

}

@BindingAdapter("bindStatusToLocalRepositoryKey")
fun bindStatusToLocalRepositoryKey(spinner: Spinner, key: String?) {
    if(key == null) {
        return
    }

    val localRepository = getKoinInstance<LocalRepository>()
    val adapter = ArrayAdapter(spinner.context, android.R.layout.simple_spinner_item, listOf("INITIAL","LINKED"))

    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    spinner.adapter = adapter
    spinner.onItemSelectedListener = AdapterOnSpinnerItemSelected<String>{ localRepository.save(key, it) }

    val selected = localRepository.retrieve(key) ?: "INITIAL"
    val selection = if(selected == "INITIAL") 0 else 1
    spinner.setSelection(selection)
}

@Suppress("UNCHECKED_CAST")
class AdapterOnSpinnerItemSelected<Item>(
    val onItemSelected: (Item) -> Unit
) : AdapterView.OnItemSelectedListener {

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val item = parent?.getItemAtPosition(position) ?: return
        onItemSelected(item as Item)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

}
