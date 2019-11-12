package com.coding.wk.footballapplication.fragments.team

import com.coding.wk.footballapplication.models.league.LeagueResponse
import com.coding.wk.footballapplication.models.team.TeamResponse
import com.coding.wk.footballapplication.networks.ApiRepository
import com.coding.wk.footballapplication.networks.TheSportDBApi
import com.coding.wk.footballapplication.utils.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TeamPresenter(private val teamView: TeamView?, private val apiRepository: ApiRepository, private val gson: Gson, private val context: CoroutineContextProvider = CoroutineContextProvider()){
    private lateinit var jobTeam: Job
    private lateinit var jobListLeague: Job
    fun getListTeam(leagueId: String?){
        jobTeam = GlobalScope.launch(context.main) {
            teamView?.showLoading()
            val data = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getAllTeam(leagueId)).await(), TeamResponse::class.java)
            try {
                teamView?.showTeamList(data.teams)
            }
            catch (e: NullPointerException){
                teamView?.showEmpty()
            }
            teamView?.hideLoading()
        }
        jobTeam.start()
    }
    fun getListLeague(){
        jobListLeague = GlobalScope.launch(context.main) {
            teamView?.showLoading()
            val league: LeagueResponse? = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getLeague()).await(), LeagueResponse::class.java)
            teamView?.showLeagues(league?.leagues?.filter { it.leagueSport.equals("Soccer") })
            teamView?.hideLoading()
        }
        jobListLeague.start()
    }
    fun searchTeam(teamName: String?){
        teamView?.showLoading()
        GlobalScope.launch(context.main) {
            val data: TeamResponse? = gson.fromJson(apiRepository.doRequest(TheSportDBApi.searchTeam(teamName)).await(), TeamResponse::class.java)
            try{
                teamView?.showTeamList(data?.teams?.filter { it.teamSport.equals("Soccer") })
            }
            catch (e: NullPointerException){
                teamView?.showEmpty()
            }
            teamView?.hideLoading()
        }
    }
    fun cancelCoroutine(){
        if(jobListLeague.isActive && !(jobListLeague.isCompleted)){
            jobListLeague.cancel()
        }
        if(jobTeam.isActive && !(jobTeam.isCompleted)){
            jobTeam.cancel()
        }
    }
}