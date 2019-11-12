package com.coding.wk.footballapplication.activities.detail.match

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import com.coding.wk.footballapplication.databases.database
import com.coding.wk.footballapplication.models.event.EventResponse
import com.coding.wk.footballapplication.models.match.MatchModels
import com.coding.wk.footballapplication.models.team.TeamResponse
import com.coding.wk.footballapplication.networks.ApiRepository
import com.coding.wk.footballapplication.networks.TheSportDBApi
import com.coding.wk.footballapplication.utils.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.toast

class MatchDetailPresenter(private val detailView: MatchDetailView, private val apiRepository: ApiRepository, private val gson: Gson,
                           private val context: Context, private val contextProvider: CoroutineContextProvider = CoroutineContextProvider()
) {

    private suspend fun getTeam(teamId: String?) =
        gson.fromJson(apiRepository.doRequest(TheSportDBApi.getDetailTeam(teamId)).await(), TeamResponse::class.java)

    fun getDetailTeam(homeId: String?, awayId: String?){
        detailView.showLoading()
        GlobalScope.launch(contextProvider.main) {
            val home = getTeam(homeId)
            val away = getTeam(awayId)
            detailView.hideLoading()
            detailView.showDetailTeam(home.teams, away.teams)
        }
    }
    fun getDetailEvent(eventId: String?){
        detailView.showLoading()
        GlobalScope.launch(contextProvider.main){
            val data = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getDetailEvent(eventId)).await(), EventResponse::class.java)
            detailView.showDetailEvent(data.events)
            detailView.hideLoading()
        }
    }
    fun addRowMatch(data: MatchModels){
        try{
            context.database.use {
                insert(
                    MatchModels.TABLE_FAVORITE_MATCH,
                    MatchModels.EVENT_ID to data.eventId,
                    MatchModels.HOME_ID to data.homeId,
                    MatchModels.AWAY_ID to data.awayId,
                    MatchModels.EVENT_LEAGUE to data.eventLeague,
                    MatchModels.EVENT_NAME to data.eventName,
                    MatchModels.EVENT_DATE to data.eventDate,
                    MatchModels.EVENT_TIME to data.eventTime,
                    MatchModels.HOME_NAME to data.homeName,
                    MatchModels.AWAY_NAME to data.awayName,
                    MatchModels.HOME_SCORE to data.homeScore,
                    MatchModels.AWAY_SCORE to data.awayScore)
            }
        }
        catch (e: SQLiteConstraintException){
            context.toast(e.localizedMessage)
        }
    }
    fun removeRowMatch(data: MatchModels){
        try{
            context.database.use {
                delete(MatchModels.TABLE_FAVORITE_MATCH, MatchModels.EVENT_ID+"={id}", "id" to data.eventId.toString())
            }

        }
        catch (e: SQLiteConstraintException){
            context.toast(e.localizedMessage)
        }
    }
}