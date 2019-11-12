package com.coding.wk.footballapplication.fragments.team

import com.coding.wk.footballapplication.contextprovider.ContextProvider
import com.coding.wk.footballapplication.models.league.LeagueModels
import com.coding.wk.footballapplication.models.league.LeagueResponse
import com.coding.wk.footballapplication.models.team.TeamModels
import com.coding.wk.footballapplication.models.team.TeamResponse
import com.coding.wk.footballapplication.networks.ApiRepository
import com.coding.wk.footballapplication.networks.TheSportDBApi
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Test

import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class TeamPresenterTest {
    private lateinit var teamPresenter: TeamPresenter

    @Mock private lateinit var teamView: TeamView
    @Mock private lateinit var apiRepository: ApiRepository
    @Mock private lateinit var gson: Gson
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        teamPresenter = TeamPresenter(teamView, apiRepository, gson, ContextProvider())
    }

    @Test
    fun getListTeam() {
        val leagueId = "4328"
        val teamLists: MutableList<TeamModels> = mutableListOf()
        val teamResponse = TeamResponse(teamLists)
        GlobalScope.launch {
            `when`(gson.fromJson(apiRepository.doRequest(TheSportDBApi.getAllTeam(leagueId)).await(), TeamResponse::class.java))
                .thenReturn(teamResponse)
            teamPresenter.getListTeam(leagueId)
            Mockito.verify(teamView).showLoading()
            Mockito.verify(teamView).showTeamList(teamLists)
            Mockito.verify(teamView).hideLoading()
            Mockito.verify(teamView).showEmpty()
        }
    }

    @Test
    fun getListLeague() {
        val leagueLists: MutableList<LeagueModels> = mutableListOf()
        val leagueResponse = LeagueResponse(leagueLists)
        GlobalScope.launch {
            `when`(gson.fromJson(apiRepository.doRequest(TheSportDBApi.getLeague()).await(), LeagueResponse::class.java))
                .thenReturn(leagueResponse)
            teamPresenter.getListLeague()
            Mockito.verify(teamView).showLoading()
            Mockito.verify(teamView).showLeagues(leagueLists)
            Mockito.verify(teamView).hideLoading()
        }
    }

    @Test
    fun searchTeam() {
        val teamName = "Barcelona"
        val teamLists: MutableList<TeamModels> = mutableListOf()
        val teamResponse = TeamResponse(teamLists)
        GlobalScope.launch {
            `when`(gson.fromJson(apiRepository.doRequest(TheSportDBApi.searchTeam(teamName)).await(), TeamResponse::class.java))
                .thenReturn(teamResponse)
            teamPresenter.getListLeague()
            Mockito.verify(teamView).showLoading()
            Mockito.verify(teamView).showTeamList(teamLists)
            Mockito.verify(teamView).hideLoading()
            Mockito.verify(teamView).showEmpty()
        }
    }
}