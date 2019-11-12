package com.coding.wk.footballapplication.fragments.team.allplayer

import com.coding.wk.footballapplication.models.player.PlayerResponse
import com.coding.wk.footballapplication.networks.ApiRepository
import com.coding.wk.footballapplication.networks.TheSportDBApi
import com.coding.wk.footballapplication.utils.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AllPlayerPresenter(private val playerView: AllPlayerView, private val apiRepository: ApiRepository, private val gson: Gson, private val context: CoroutineContextProvider = CoroutineContextProvider()) {
   fun getPlayerAll(teamId: String?){
       playerView.showLoading()
       GlobalScope.launch(context.main) {
           val data = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getAllPlayer(teamId)).await(), PlayerResponse::class.java)
           playerView.showAllPlayer(data.player)
           playerView.hideLoading()
       }
   }
}