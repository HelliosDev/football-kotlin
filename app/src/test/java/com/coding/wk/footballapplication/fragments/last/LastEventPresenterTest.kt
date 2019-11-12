package com.coding.wk.footballapplication.fragments.last

import com.coding.wk.footballapplication.contextprovider.ContextProvider
import com.coding.wk.footballapplication.fragments.match.EventView
import com.coding.wk.footballapplication.fragments.match.last.LastEventPresenter
import com.coding.wk.footballapplication.models.match.MatchModels
import com.coding.wk.footballapplication.models.match.MatchResponse
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

class LastEventPresenterTest {
    private lateinit var lastPresenter: LastEventPresenter

    @Mock private lateinit var lastView: EventView
    @Mock private lateinit var apiRepository: ApiRepository
    @Mock private lateinit var gson: Gson

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        lastPresenter = LastEventPresenter(lastView, apiRepository, gson, ContextProvider())
    }

    @Test
    fun getLastEventList() {
        val leagueId = "4328"
        val lastMatches: MutableList<MatchModels> = mutableListOf()
        val matchResponse = MatchResponse(lastMatches)
        GlobalScope.launch {
            `when`(gson.fromJson(apiRepository.doRequest(TheSportDBApi.getLastEvents(leagueId)).await(), MatchResponse::class.java))
                .thenReturn(matchResponse)
            lastPresenter.getLastEventList(leagueId)
            Mockito.verify(lastView).showLoading()
            Mockito.verify(lastView).showEventList(lastMatches)
            Mockito.verify(lastView).hideLoading()
        }
    }
}