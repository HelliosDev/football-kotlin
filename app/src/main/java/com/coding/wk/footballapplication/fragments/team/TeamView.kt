package com.coding.wk.footballapplication.fragments.team

import com.coding.wk.footballapplication.models.league.LeagueModels
import com.coding.wk.footballapplication.models.team.TeamModels


interface TeamView {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<TeamModels>?)
    fun showLeagues(data: List<LeagueModels>?)
    fun showEmpty()
}