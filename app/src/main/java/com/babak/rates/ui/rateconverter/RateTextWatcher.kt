package com.babak.rates.ui.rateconverter

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import java.lang.ref.WeakReference

class RateTextWatcher : TextWatcher {
    private var position = 0
    private lateinit var editText: WeakReference<EditText>
    private lateinit var rateListener: WeakReference<RateAdapterInterface>

    fun updatePosition(position: Int, valueEditText: EditText, listener: RateAdapterInterface) {
        this.position = position
        this.editText = WeakReference(valueEditText)
        this.rateListener = WeakReference(listener)
    }

    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        Log.d("Test", s.toString() + " pos:" + position)
        if (editText.get()?.hasFocus()!! && s?.isNotEmpty()!!) {
            rateListener.get()?.onTextChange(s.toString())
        }
    }
}