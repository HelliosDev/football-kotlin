package com.coding.wk.footballapplication.fragments.favorite

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.coding.wk.footballapplication.R
import com.coding.wk.footballapplication.adapters.FCAdapter
import com.coding.wk.footballapplication.databases.database
import com.coding.wk.footballapplication.models.match.MatchModels
import kotlinx.android.synthetic.main.fragment_fav_match.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.onRefresh

class FavoriteMatchFragment : Fragment(){
    private lateinit var adapter: FCAdapter
    private var matchLists: MutableList<MatchModels> = mutableListOf()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_fav_match, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = FCAdapter(matchLists)
        rv_favorites.layoutManager = LinearLayoutManager(context)
        rv_favorites.adapter = adapter
        displayFavMatch()
        fav__match_sr.onRefresh {
            matchLists.clear()
            displayFavMatch()
        }
    }

    private fun displayFavMatch(){
        context?.database?.use {
            fav__match_sr.isRefreshing = false
            matchLists.clear()
            val rowResult = select(MatchModels.TABLE_FAVORITE_MATCH).parseList(classParser<MatchModels>())
            matchLists.addAll(rowResult)
            adapter.notifyDataSetChanged()
        }
    }

}