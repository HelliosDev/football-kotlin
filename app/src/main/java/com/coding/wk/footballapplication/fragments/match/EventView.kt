package com.coding.wk.footballapplication.fragments.match

import com.coding.wk.footballapplication.models.league.LeagueModels
import com.coding.wk.footballapplication.models.match.MatchModels


interface EventView {
    fun showLoading()
    fun hideLoading()
    fun showEventList(data: List<MatchModels>?)
    fun showLeagues(data: List<LeagueModels>?)
    fun showEmpty()
}