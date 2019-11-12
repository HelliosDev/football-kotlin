package com.coding.wk.footballapplication.fragments.match.next

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
import kotlinx.android.synthetic.main.fragment_event_next.*

class NextEventFragment: Fragment(), EventView {

    private lateinit var presenter: NextEventPresenter
    private lateinit var adapter: FCAdapter
    private var leagueLists: MutableList<LeagueModels> = mutableListOf()
    private var matchLists: MutableList<MatchModels> = mutableListOf()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_event_next, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = FCAdapter(matchLists, true)
        rv_next.layoutManager = LinearLayoutManager(context)
        rv_next.adapter = adapter
        presenter = NextEventPresenter(this, ApiRepository(), Gson())
        presenter.getListLeague()
        retainInstance = true
    }

    override fun showLoading() {
        progress_bar.visible()
        rv_next.invisible()
        empty_next.invisible()
    }

    override fun hideLoading() {
        progress_bar.invisible()
        rv_next.visible()
        empty_next.invisible()
    }

    override fun showEmpty() {
        progress_bar.invisible()
        rv_next.invisible()
        empty_next.visible()
    }

    override fun showEventList(data: List<MatchModels>?) {
        matchLists.clear()
        if (data != null) {
            matchLists.addAll(data)
        }
        adapter.notifyDataSetChanged()
    }
    override fun showLeagues(data: List<LeagueModels>?) {
        leagueLists.clear()
        if (data != null) {
            leagueLists.addAll(data)
        }
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, leagueLists)
        spinner_next.adapter = spinnerAdapter
        spinner_next.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val leagueName: LeagueModels = spinner_next.selectedItem as LeagueModels
                presenter.getNextEventList(leagueName.leagueId)
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