package com.babak.rates.ui.rateconverter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.babak.rates.R
import com.babak.rates.ui.model.Rate
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_rates.*
import javax.inject.Inject


class RatesFragment : DaggerFragment(), RateAdapterInterface {

    private val adapter = RatesAdapter()
    private lateinit var linearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val ratesViewModel by viewModels<RatesViewModel> { viewModelFactory }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rates, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        ratesRecyclerView.layoutManager = linearLayoutManager

        adapter.listener = this
        ratesRecyclerView.adapter = adapter

        ratesViewModel.items.observe(viewLifecycleOwner, Observer { data ->

            adapter.updateData(data)

        })

        ratesViewModel.isChangeCurrency.observe(viewLifecycleOwner, Observer {
            if (it) {
                linearLayoutManager.scrollToPositionWithOffset(0, 0)
            }
        })

        ratesViewModel.dataLoading.observe(viewLifecycleOwner, Observer {
            if (it) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        })

        ratesViewModel.error.observe(viewLifecycleOwner, Observer {
            Snackbar.make(view, it.message, Snackbar.LENGTH_SHORT).show()
        })


    }

    override fun onItemClick(rate: Rate) {
        ratesViewModel.changeBaseCurrency(rate.currency)
    }

    override fun onTextChange(newValue: String) {
        ratesViewModel.valueChanged(newValue)
    }


}
