package com.coding.wk.footballapplication.fragments.match.next

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

class NextEventPresenter(private val nextView: EventView?, private val apiRepository: ApiRepository, private val gson: Gson, private val context: CoroutineContextProvider = CoroutineContextProvider()) {
    private lateinit var jobNextEvent: Job
    private lateinit var jobListLeague: Job
    fun getNextEventList(leagueId: String?) {
        jobNextEvent = GlobalScope.launch(context.main) {
            nextView?.showLoading()
            val data: MatchResponse? = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getNextEvents(leagueId)).await(), MatchResponse::class.java)
            try{
                nextView?.showEventList(data?.events)
            }
            catch (e: Exception){
                nextView?.showEmpty()
            }
            nextView?.hideLoading()
        }
        jobNextEvent.start()
    }
    fun getListLeague(){
        jobListLeague = GlobalScope.launch(context.main) {
            nextView?.showLoading()
            val league: LeagueResponse? = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getLeague()).await(), LeagueResponse::class.java)
            nextView?.showLeagues(league?.leagues?.filter { it.leagueSport.equals("Soccer") })
            nextView?.hideLoading()
        }
        jobListLeague.start()
    }
    fun cancelCoroutine(){
        if(jobListLeague.isActive && !(jobListLeague.isCompleted)){
            jobListLeague.cancel()
        }
        if(jobNextEvent.isActive && !(jobNextEvent.isCompleted)){
            jobNextEvent.cancel()
        }
    }
}