package com.coding.wk.footballapplication.activities.detail.match

import com.coding.wk.footballapplication.models.event.EventModels
import com.coding.wk.footballapplication.models.team.TeamModels

interface MatchDetailView {
    fun showLoading()
    fun hideLoading()
    fun showDetailTeam(dataHome: List<TeamModels>, dataAway: List<TeamModels>)
    fun showDetailEvent(data: List<EventModels>)
}