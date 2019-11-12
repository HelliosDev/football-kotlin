package com.coding.wk.footballapplication.activities.detail.match

import android.content.Context
import com.coding.wk.footballapplication.contextprovider.ContextProvider
import com.coding.wk.footballapplication.models.event.EventModels
import com.coding.wk.footballapplication.models.event.EventResponse
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
import org.mockito.MockitoAnnotations

class MatchDetailPresenterTest {
    private lateinit var presenter: MatchDetailPresenter
    private suspend fun getTeam(teamId: String?) = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getDetailTeam(teamId)).await(), TeamResponse::class.java)
    @Mock private lateinit var matchDetailView: MatchDetailView
    @Mock private lateinit var apiRepository: ApiRepository
    @Mock private lateinit var gson: Gson
    @Mock private lateinit var context: Context
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MatchDetailPresenter(matchDetailView, apiRepository, gson, context, ContextProvider())
    }

    @Test
    fun getDetailTeam() {
        val homeId = "133604"
        val awayId = "133610"
        val homeList: List<TeamModels> = mutableListOf()
        val awayList: List<TeamModels> = mutableListOf()
        val homeResponse = TeamResponse(homeList)
        val awayResponse = TeamResponse(awayList)

        GlobalScope.launch {
            Mockito.`when`(getTeam(homeId)).thenReturn(homeResponse)
            Mockito.`when`(getTeam(awayId)).thenReturn(awayResponse)

            presenter.getDetailTeam(homeId, awayId)
            Mockito.verify(matchDetailView).showLoading()
            Mockito.verify(matchDetailView).showDetailTeam(homeList, awayList)
            Mockito.verify(matchDetailView).hideLoading()
        }
    }

    @Test
    fun getDetailEvent() {
        val eventId = "441613"
        val eventList: List<EventModels> = mutableListOf()
        val eventResponse = EventResponse(eventList)
        GlobalScope.launch{
            Mockito.`when`(
                gson.fromJson(
                    apiRepository.doRequest(TheSportDBApi.getDetailEvent(eventId)).await(),
                    EventResponse::class.java
                )
            )
                .thenReturn(eventResponse)
            presenter.getDetailEvent(eventId)
            Mockito.verify(matchDetailView).showLoading()
            Mockito.verify(matchDetailView).showDetailEvent(eventList)
            Mockito.verify(matchDetailView).hideLoading()
        }
    }
}