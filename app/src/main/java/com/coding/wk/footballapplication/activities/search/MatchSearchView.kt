package com.coding.wk.footballapplication.activities.search

import com.coding.wk.footballapplication.models.match.MatchModels

interface MatchSearchView {
    fun showLoading()
    fun hideLoading()
    fun showEventList(data: List<MatchModels>)
    fun showEmpty()
}