package com.coding.wk.footballapplication.networks

import com.coding.wk.footballapplication.BuildConfig

object TheSportDBApi {
    fun getNextEvents(leagueId: String?): String =
        getEvent("eventsnextleague.php", leagueId)

    fun getLastEvents(leagueId: String?): String =
        getEvent("eventspastleague.php", leagueId)

    fun getDetailTeam(teamId: String?): String =
        getEvent("lookupteam.php", teamId)

    fun getDetailPlayer(playerId: String?): String =
        getEvent("lookupplayer.php", playerId)

    fun getDetailEvent(eventId: String?): String =
        getEvent("lookupevent.php", eventId)

    fun getLeague(): String =
        getEvent("all_leagues.php", null)

    fun getAllTeam(leagueId: String?): String =
        getEvent("lookup_all_teams.php", leagueId)

    fun getAllPlayer(teamId: String?): String = getEvent("lookup_all_players.php", teamId)

    fun searchTeam(teamName: String?) =
        searchUrl("searchteams.php", "t", teamName)

    fun searchEvent(eventName: String?)=
        searchUrl("searchevents.php", "e", eventName)

    private fun getEvent(segment: String?, id: String?): String =
        BuildConfig.BASE_URL+"api/v1/json/${BuildConfig.API_KEY}/" +segment+
                if(id != null)"?id=$id" else ""

    private fun searchUrl(segment: String?, query: String?, name: String?): String =
        BuildConfig.BASE_URL+"api/v1/json/${BuildConfig.API_KEY}/" +segment+"?$query=$name"
}