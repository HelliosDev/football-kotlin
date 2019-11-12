package com.coding.wk.footballapplication.activities.search

import com.coding.wk.footballapplication.models.match.MatchesResponse
import com.coding.wk.footballapplication.networks.ApiRepository
import com.coding.wk.footballapplication.networks.TheSportDBApi
import com.coding.wk.footballapplication.utils.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MatchSearchPresenter(private val searchView: MatchSearchView, private val apiRepository: ApiRepository, private val gson: Gson,
                           private val context: CoroutineContextProvider = CoroutineContextProvider()) {
    private lateinit var jobSearch: Job


    fun searchEvent(eventName: String?){
        jobSearch = GlobalScope.launch(context.main) {
            searchView.showLoading()
            val data = gson.fromJson(apiRepository.doRequest(TheSportDBApi.searchEvent(eventName)).await(), MatchesResponse::class.java)
            try{
                searchView.showEventList(data.event.filter { it.eventLeague.equals("Soccer") })
            }
            catch(e: NullPointerException){
                searchView.showEmpty()
            }
            searchView.hideLoading()
        }
        jobSearch.start()
    }
    fun cancelCoroutine(){
        if(jobSearch.isActive && !(jobSearch.isCompleted)){
            jobSearch.cancel()
        }
    }
}