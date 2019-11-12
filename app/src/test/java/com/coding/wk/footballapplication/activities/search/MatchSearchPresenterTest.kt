package com.coding.wk.footballapplication.activities.search

import com.coding.wk.footballapplication.contextprovider.ContextProvider
import com.coding.wk.footballapplication.models.match.MatchModels
import com.coding.wk.footballapplication.models.match.MatchesResponse
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

class MatchSearchPresenterTest {
    private lateinit var matchSearchPresenter: MatchSearchPresenter
    @Mock private lateinit var matchSearchView: MatchSearchView
    @Mock private lateinit var apiRepository: ApiRepository
    @Mock private lateinit var gson: Gson
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        matchSearchPresenter = MatchSearchPresenter(matchSearchView, apiRepository, gson, ContextProvider())
    }

    @Test
    fun searchEvent() {
        val eventName = "Arsenal_vs_Chelsea"
        val eventLists: List<MatchModels> = mutableListOf()
        val eventResponse = MatchesResponse(eventLists)
        GlobalScope.launch {
            `when`(gson.fromJson(apiRepository.doRequest(TheSportDBApi.searchEvent(eventName)).await(), MatchesResponse::class.java))
                .thenReturn(eventResponse)
            matchSearchPresenter.searchEvent(eventName)
            Mockito.verify(matchSearchView).showLoading()
            Mockito.verify(matchSearchView).showEventList(eventLists)
            Mockito.verify(matchSearchView).hideLoading()
            Mockito.verify(matchSearchView).showEmpty()
        }
    }
}