package com.coding.wk.footballapplication.activities.search

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.widget.SearchView
import com.coding.wk.footballapplication.R
import com.coding.wk.footballapplication.adapters.FCAdapter
import com.coding.wk.footballapplication.models.match.MatchModels
import com.coding.wk.footballapplication.networks.ApiRepository
import com.coding.wk.footballapplication.utils.invisible
import com.coding.wk.footballapplication.utils.visible
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_search_match.*

class MatchSearchActivity: AppCompatActivity(), MatchSearchView {
    private lateinit var presenter: MatchSearchPresenter
    private lateinit var adapter: FCAdapter
    private var matchLists: MutableList<MatchModels> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_match)
        presenter = MatchSearchPresenter(this, ApiRepository(), Gson())
        adapter = FCAdapter(matchLists)
        rv_match_search.layoutManager = LinearLayoutManager(this)
        rv_match_search.adapter = adapter
    }
    override fun showLoading() {
        match_search_pb.visible()
        rv_match_search.invisible()
        empty_search.invisible()
    }

    override fun hideLoading() {
        match_search_pb.invisible()
        rv_match_search.visible()
        empty_search.invisible()
    }

    override fun showEmpty() {
        match_search_pb.invisible()
        rv_match_search.invisible()
        empty_search.visible()
    }

    override fun showEventList(data: List<MatchModels>) {
        matchLists.clear()
        matchLists.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val searchItem = menu?.findItem(R.id.searchview_menu)
        searchItem?.expandActionView()
        val searchEvent = searchItem?.actionView as SearchView
        searchEvent.queryHint = resources.getString(R.string.event_hint)
        searchEvent.setIconifiedByDefault(false)
        searchEvent.requestFocusFromTouch()
        searchProcess(searchEvent)
        return super.onCreateOptionsMenu(menu)
    }

    private fun searchProcess(search: SearchView){
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d(MatchSearchActivity::class.java.simpleName, "text adalah: $newText")
                presenter.searchEvent(newText)
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