package com.babak.rates.ui

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.babak.rates.R
import com.babak.rates.util.getCurrencyNameResourceId
import com.babak.rates.util.getFlagResourceId
import com.bumptech.glide.Glide
import java.text.DecimalFormat
import java.util.*


class RatesAdapter :
    RecyclerView.Adapter<RatesAdapter.ViewHolder>() {


    private val decimalFormat = DecimalFormat("##.####")

    var listener: AdapterInterface? = null

    private val ratesData = mutableListOf<Rate>()

    fun updateData(newList: List<Rate>) {
        val diffResult = DiffUtil.calculateDiff(RateDiffCallback(ratesData, newList))
        diffResult.dispatchUpdatesTo(this)
        ratesData.clear()
        ratesData.addAll(newList)
    }


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

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            return super.onBindViewHolder(holder, position, payloads)
        } else {

            val combinedChange = createCombinedPayload(payloads as List<Change<Rate>>)
            val oldData = combinedChange.oldData
            val newData = combinedChange.newData

            holder.bindPayLoad(oldData, newData)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val rate = ratesData[position]
        holder.bind(rate)
    }


    inner class ViewHolder(private val view: View, positionTextWatcher: PositionTextWatcher) :
        RecyclerView.ViewHolder(view) {

        private val currencyTextView = view.findViewById<TextView>(R.id.currencyTextView)
        private val currencyNameTextView = view.findViewById<TextView>(R.id.currencyNameTextView)
        private val valueEditText = view.findViewById<EditText>(R.id.valueEditText)
        private val countryImageView =
            view.findViewById<AppCompatImageView>(R.id.countryFlagImageView)
        private val textWatcher = positionTextWatcher

        fun bindPayLoad(oldData: Rate, newData: Rate) {
            if (newData.value != oldData.value) {
                valueEditText.setText(decimalFormat.format(newData.value))
            }

            if (newData.currency != oldData.currency) {
                currencyTextView.text = newData.currency
            }

            if (adapterPosition == 0) {
                valueEditText.requestFocus()
                valueEditText.addTextChangedListener(textWatcher)
                valueEditText.setSelection(valueEditText.text.length)
                textWatcher.updatePosition(adapterPosition, valueEditText)
            } else {
                valueEditText.clearFocus()
                valueEditText.removeTextChangedListener(textWatcher)
            }

            view.setOnClickListener {
                if (adapterPosition != 0) {
                    listener?.onItemClick(newData)
                }
            }

        }

        fun bind(rate: Rate) {
            if (adapterPosition == 0) {
                valueEditText.requestFocus()
                valueEditText.addTextChangedListener(textWatcher)
                valueEditText.setSelection(valueEditText.text.length)
                textWatcher.updatePosition(adapterPosition, valueEditText)
            } else {
                valueEditText.clearFocus()
                valueEditText.removeTextChangedListener(textWatcher)
            }

            val currencySymbolLowerCase = rate.currency.toLowerCase(Locale.ENGLISH)

            Glide
                .with(view)
                .load(getFlagResourceId(view.context, currencySymbolLowerCase))
                .placeholder(R.drawable.ic_flag_default)
                .into(countryImageView)

            currencyNameTextView.text = view.resources.getText(
                getCurrencyNameResourceId(
                    view.context,
                    currencySymbolLowerCase
                )
            )
            currencyTextView.text = rate.currency
            valueEditText.setText(decimalFormat.format(rate.value))
            view.setOnClickListener {
                if (adapterPosition != 0) {
                    listener?.onItemClick(rate)
                }
            }
        }

    }

    inner class PositionTextWatcher : TextWatcher {

        private var position = 0
        private lateinit var editText: EditText

        fun updatePosition(position: Int, valueEditText: EditText) {
            this.position = position
            this.editText = valueEditText
        }

        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            Log.d("Test", s.toString() + " pos:" + position)
            if (editText.hasFocus() && s?.isNotEmpty()!!) {
                listener?.onTextChange(s.toString())
            }
        }
    }

    interface AdapterInterface {
        fun onItemClick(rate: Rate)
        fun onTextChange(newValue: String)
    }

    class RateDiffCallback(private val oldList: List<Rate>, private val newList: List<Rate>) :
        DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].currency == newList[newItemPosition].currency
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

        override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {

            return Change(
                oldList[oldItemPosition],
                newList[newItemPosition]
            )
        }

    }
}