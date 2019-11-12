package com.coding.wk.footballapplication.fragments.team

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import com.coding.wk.footballapplication.R
import com.coding.wk.footballapplication.adapters.FCTeamAdapter
import com.coding.wk.footballapplication.models.league.LeagueModels
import com.coding.wk.footballapplication.models.team.TeamModels
import com.coding.wk.footballapplication.networks.ApiRepository
import com.coding.wk.footballapplication.utils.invisible
import com.coding.wk.footballapplication.utils.visible
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_team.*

class TeamFragment: Fragment(), TeamView {

    private lateinit var presenter: TeamPresenter
    private lateinit var adapter: FCTeamAdapter
    private lateinit var leagueName: LeagueModels
    private var teamLists: MutableList<TeamModels> = mutableListOf()
    private var leagueLists: MutableList<LeagueModels> = mutableListOf()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_team, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        adapter = FCTeamAdapter(teamLists)
        rv_team.layoutManager = GridLayoutManager(context, 2)
        rv_team.adapter = adapter
        presenter = TeamPresenter(this, ApiRepository(), Gson())
        presenter.getListLeague()
    }

    override fun showLoading() {
        team_progress_bar.visible()
        empty_team.invisible()
        rv_team.invisible()
    }

    override fun hideLoading() {
        team_progress_bar.invisible()
        empty_team.invisible()
        rv_team.visible()
    }

    override fun showEmpty() {
        team_progress_bar.invisible()
        empty_team.visible()
        rv_team.invisible()
    }

    override fun showTeamList(data: List<TeamModels>?) {
        teamLists.clear()
        if (data != null) {
            teamLists.addAll(data)
        }
        adapter.notifyDataSetChanged()
    }
    override fun showLeagues(data: List<LeagueModels>?) {
        leagueLists.clear()
        if(data != null) leagueLists.addAll(data)
        Log.d(TeamFragment::class.java.simpleName, "Isi league: "+leagueLists[0])
        Log.d(TeamFragment::class.java.simpleName, "Isi league: "+leagueLists[1])
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, leagueLists)
        spinner_team.adapter = spinnerAdapter
        spinner_team.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                leagueName = spinner_team.selectedItem as LeagueModels
                presenter.getListTeam(leagueName.leagueId)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        adapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.search_menu, menu)
        val searchItem = menu?.findItem(R.id.searchview_menu)
        searchItem?.expandActionView()
        val searchTeam = searchItem?.actionView as SearchView
        searchTeam.queryHint = resources.getString(R.string.team_hint)
        searchProcess(searchTeam)
    }

    private fun searchProcess(search: SearchView){
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {
                spinner_team.invisible()
                presenter.searchTeam(newText)
                return true
            }
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.cancelCoroutine()
    }
}