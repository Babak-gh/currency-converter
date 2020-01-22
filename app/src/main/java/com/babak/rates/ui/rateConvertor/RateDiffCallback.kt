package com.babak.rates.ui.rateConvertor

import androidx.recyclerview.widget.DiffUtil
import com.babak.rates.ui.model.Rate
import com.babak.rates.util.Change

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