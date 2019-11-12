package com.coding.wk.footballapplication.fragments.match.last

import com.coding.wk.footballapplication.fragments.match.EventView
import com.coding.wk.footballapplication.models.league.LeagueResponse
import com.coding.wk.footballapplication.models.match.MatchResponse
import com.coding.wk.footballapplication.networks.ApiRepository
import com.coding.wk.footballapplication.networks.TheSportDBApi
import com.coding.wk.footballapplication.utils.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LastEventPresenter(private val lastView: EventView?, private val apiRepository: ApiRepository, private val gson: Gson, private val context: CoroutineContextProvider = CoroutineContextProvider()) {
    private lateinit var jobLastEvent: Job
    private lateinit var jobListLeague: Job
    fun getLastEventList(leagueId: String?) {
        jobLastEvent = GlobalScope.launch(context.main) {
            lastView?.showLoading()
            val data: MatchResponse? = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getLastEvents(leagueId)).await(), MatchResponse::class.java)
            try {
                lastView?.showEventList(data?.events)
            }
            catch (e: Exception){
                lastView?.showEmpty()
            }
            lastView?.hideLoading()
        }
        jobLastEvent.start()
    }
    fun getListLeague(){
        jobListLeague = GlobalScope.launch(context.main) {
            lastView?.showLoading()
            val league: LeagueResponse? = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getLeague()).await(), LeagueResponse::class.java)
            lastView?.showLeagues(league?.leagues?.filter { it.leagueSport.equals("Soccer") })
            lastView?.hideLoading()
        }
        jobListLeague.start()
    }

    fun cancelCoroutine(){
        if(jobListLeague.isActive && !(jobListLeague.isCompleted)){
            jobListLeague.cancel()
        }
        if(jobLastEvent.isActive && !(jobLastEvent.isCompleted)){
            jobLastEvent.cancel()
        }
    }
}