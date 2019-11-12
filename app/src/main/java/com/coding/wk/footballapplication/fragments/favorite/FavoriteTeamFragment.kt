package com.coding.wk.footballapplication.fragments.favorite

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.coding.wk.footballapplication.R
import com.coding.wk.footballapplication.adapters.FCTeamAdapter
import com.coding.wk.footballapplication.databases.database
import com.coding.wk.footballapplication.models.team.TeamModels
import kotlinx.android.synthetic.main.fragment_fav_team.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.onRefresh

class FavoriteTeamFragment: Fragment() {
    private lateinit var adapter: FCTeamAdapter
    private var teamLists: MutableList<TeamModels> = mutableListOf()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_fav_team, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = FCTeamAdapter(teamLists)
        rv_fav_team.layoutManager = GridLayoutManager(context, 2)
        rv_fav_team.adapter = adapter
        displayFavTeam()
        fav_team_sr.onRefresh {
            teamLists.clear()
            displayFavTeam()
        }
    }

    private fun displayFavTeam(){
        context?.database?.use{
            fav_team_sr.isRefreshing = false
            teamLists.clear()
            val rowResult = select(TeamModels.TABLE_FAVORITE_TEAM).parseList(classParser<TeamModels>())
            teamLists.addAll(rowResult)
            adapter.notifyDataSetChanged()
        }
    }
}