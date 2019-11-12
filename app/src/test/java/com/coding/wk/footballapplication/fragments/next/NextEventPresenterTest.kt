package com.coding.wk.footballapplication.fragments.next

import com.coding.wk.footballapplication.fragments.match.EventView
import com.coding.wk.footballapplication.fragments.match.next.NextEventPresenter
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

class NextEventPresenterTest {
    private lateinit var nextPresenter: NextEventPresenter

    @Mock private lateinit var nextView: EventView
    @Mock private lateinit var apiRepository: ApiRepository
    @Mock private lateinit var gson: Gson

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        nextPresenter = NextEventPresenter(nextView, apiRepository, gson)
    }

    @Test
    fun getNextEventList() {
        val leagueId = "4328"
        val nextMatches: MutableList<MatchModels> = mutableListOf()
        val matchResponse = MatchResponse(nextMatches)
        GlobalScope.launch {
            `when`(gson.fromJson(apiRepository.doRequest(TheSportDBApi.getNextEvents(leagueId)).await(), MatchResponse::class.java))
                .thenReturn(matchResponse)
            nextPresenter.getNextEventList(leagueId)
            Mockito.verify(nextView).showLoading()
            Mockito.verify(nextView).showEventList(nextMatches)
            Mockito.verify(nextView).hideLoading()
        }
    }
}