package com.coding.wk.footballapplication.fragments.team.allplayer

import com.coding.wk.footballapplication.models.player.PlayerModels

interface AllPlayerView {
    fun showLoading()
    fun hideLoading()
    fun showAllPlayer(data: List<PlayerModels>)
}