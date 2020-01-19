package com.babak.rates.ui

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.babak.rates.R
import java.text.DecimalFormat


class RatesAdapter(private val ratesData: List<Rate>) :
    RecyclerView.Adapter<RatesAdapter.ViewHolder>() {

    var listener: AdapterInterface? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.rate_row_item,
                parent,
                false
            ),
            PositionTextWatcher()
        )
    }

    override fun getItemCount(): Int {
        return ratesData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val rate = ratesData[position]
        holder.bind(rate)
    }


    inner class ViewHolder(private val view: View, positionTextWatcher: PositionTextWatcher) :
        RecyclerView.ViewHolder(view) {

        private val currencyTextView = view.findViewById<TextView>(R.id.currencyTextView)
        private val valueEditText = view.findViewById<EditText>(R.id.valueEditText)
        private val textWatcher = positionTextWatcher

        fun bind(rate: Rate) {
            if (adapterPosition == 0) {
                valueEditText.requestFocus()
                valueEditText.addTextChangedListener(textWatcher)
                textWatcher.updatePosition(adapterPosition)
            } else {
                valueEditText.clearFocus()
                valueEditText.removeTextChangedListener(textWatcher)
            }
            currencyTextView.text = rate.currency
            valueEditText.setText(DecimalFormat("##.####").format(rate.value))
            view.setOnClickListener {
                if (adapterPosition != 0) {
                    listener?.onItemClick(rate, adapterPosition)
                }
            }
        }

    }

    inner class PositionTextWatcher : TextWatcher {

        private var position = 0

        fun updatePosition(position: Int) {
            this.position = position
        }

        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            Log.d("Fuck", s.toString() + " pos:" + position)
            if (s?.isNotEmpty()!!) {
                listener?.onTextChange(s.toString())
            }
        }
    }

    interface AdapterInterface {
        fun onItemClick(rate: Rate, position: Int)
        fun onTextChange(newValue: String)
    }
}