package com.coding.wk.footballapplication.fragments.match.last

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.coding.wk.footballapplication.R
import com.coding.wk.footballapplication.adapters.FCAdapter
import com.coding.wk.footballapplication.fragments.match.EventView
import com.coding.wk.footballapplication.models.league.LeagueModels
import com.coding.wk.footballapplication.models.match.MatchModels
import com.coding.wk.footballapplication.networks.ApiRepository
import com.coding.wk.footballapplication.utils.invisible
import com.coding.wk.footballapplication.utils.visible
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_event_last.*

class LastEventFragment: Fragment(), EventView {

    private lateinit var presenter: LastEventPresenter
    private lateinit var adapter: FCAdapter
    private var matchLists: MutableList<MatchModels> = mutableListOf()
    private var leagueLists: MutableList<LeagueModels> = mutableListOf()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_event_last, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = FCAdapter(matchLists)
        rv_last.layoutManager = LinearLayoutManager(context)
        rv_last.adapter = adapter
        presenter = LastEventPresenter(this, ApiRepository(), Gson())
        presenter.getListLeague()
    }

    override fun showLoading() {
        last_progress_bar.visible()
        rv_last.invisible()
        empty_last.invisible()
    }

    override fun hideLoading() {
        last_progress_bar.invisible()
        rv_last.visible()
        empty_last.invisible()
    }

    override fun showEmpty() {
        last_progress_bar.invisible()
        rv_last.invisible()
        empty_last.visible()
    }

    override fun showEventList(data: List<MatchModels>?) {
        matchLists.clear()
        if(data != null) matchLists.addAll(data)
        adapter.notifyDataSetChanged()
    }
    override fun showLeagues(data: List<LeagueModels>?) {
        leagueLists.clear()
        if (data != null) leagueLists.addAll(data)
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, leagueLists)
        spinner_last.adapter = spinnerAdapter
        spinner_last.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val leagueName: LeagueModels = spinner_last.selectedItem as LeagueModels
                presenter.getLastEventList(leagueName.leagueId)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.cancelCoroutine()
    }
}

