package com.babak.rates.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.babak.rates.R


class RatesAdapter(private val ratesData: List<Rate>) :
    RecyclerView.Adapter<RatesAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.rate_row_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return ratesData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val rate = ratesData[position]
        holder.bind(rate)
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val currencyTextView = view.findViewById<TextView>(R.id.currencyTextView)
        private val valueEditText = view.findViewById<EditText>(R.id.valueEditText)

        fun bind(rate: Rate) {
            currencyTextView.text = rate.currency
            valueEditText.setText(rate.value.toString())
        }

    }
}