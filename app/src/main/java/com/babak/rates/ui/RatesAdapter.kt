package com.babak.rates.ui

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.babak.rates.R
import java.text.DecimalFormat


class RatesAdapter :
    RecyclerView.Adapter<RatesAdapter.ViewHolder>() {

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
        private val valueEditText = view.findViewById<EditText>(R.id.valueEditText)
        private val textWatcher = positionTextWatcher

        fun bindPayLoad(oldData: Rate, newData: Rate) {
            if (newData.value != oldData.value) {
                valueEditText.setText(DecimalFormat("##.####").format(newData.value))
            }

            if (newData.currency != oldData.currency) {
                currencyTextView.text = newData.currency
            }

            if (adapterPosition == 0) {
                valueEditText.requestFocus()
                valueEditText.addTextChangedListener(textWatcher)
                textWatcher.updatePosition(adapterPosition)
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
                textWatcher.updatePosition(adapterPosition)
            } else {
                valueEditText.clearFocus()
                valueEditText.removeTextChangedListener(textWatcher)
            }

            currencyTextView.text = rate.currency
            valueEditText.setText(DecimalFormat("##.####").format(rate.value))
            view.setOnClickListener {
                if (adapterPosition != 0) {
                    listener?.onItemClick(rate)
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
            /*return if(oldList[oldItemPosition].value != newList[newItemPosition].value){
                newList[newItemPosition].value
            }
            else{
                null
            }*/

            return Change(
                oldList[oldItemPosition],
                newList[newItemPosition]
            )
        }

    }
}