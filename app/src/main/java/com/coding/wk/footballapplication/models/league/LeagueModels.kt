package com.coding.wk.footballapplication.models.league

import com.google.gson.annotations.SerializedName

data class LeagueModels(
    @SerializedName("idLeague")
    var leagueId: String? = null,

    @SerializedName("strLeague")
    var leagueName: String? = null,

    @SerializedName("strSport")
    var leagueSport: String? = null
){ override fun toString(): String = leagueName.toString() }