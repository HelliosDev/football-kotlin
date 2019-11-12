package com.coding.wk.footballapplication.fragments.team.allplayer

import com.coding.wk.footballapplication.models.player.PlayerModels
import com.coding.wk.footballapplication.models.player.PlayerResponse
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

class AllPlayerPresenterTest {
    private lateinit var allPlayerPresenter: AllPlayerPresenter
    @Mock private lateinit var allPlayerView: AllPlayerView
    @Mock private lateinit var apiRepository: ApiRepository
    @Mock private lateinit var gson: Gson
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        allPlayerPresenter = AllPlayerPresenter(allPlayerView, apiRepository, gson)
    }

    @Test
    fun getPlayerAll() {
        val teamId = "133604"
        val allPlayers: MutableList<PlayerModels> = mutableListOf()
        val playerResponse = PlayerResponse(allPlayers)
        GlobalScope.launch {
            `when`(gson.fromJson(apiRepository.doRequest(TheSportDBApi.getAllPlayer(teamId)).await(), PlayerResponse::class.java))
                .thenReturn(playerResponse)
            allPlayerPresenter.getPlayerAll(teamId)
            Mockito.verify(allPlayerView).showLoading()
            Mockito.verify(allPlayerView).showAllPlayer(allPlayers)
            Mockito.verify(allPlayerView).hideLoading()
        }
    }
}