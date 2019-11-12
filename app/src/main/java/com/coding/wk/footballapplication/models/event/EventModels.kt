package com.coding.wk.footballapplication.models.event

import com.google.gson.annotations.SerializedName

data class EventModels(
    @SerializedName("strHomeGoalDetails")
    var homeGoalPlayer: String? = null,

    @SerializedName("strAwayGoalDetails")
    var awayGoalPlayer: String? = null,

    @SerializedName("intHomeShots")
    var homeShots: String? = null,

    @SerializedName("intAwayShots")
    var awayShots: String? = null,

    @SerializedName("strHomeLineupGoalkeeper")
    var homeGoalKeeperName: String? = null,

    @SerializedName("strAwayLineupGoalkeeper")
    var awayGoalKeeperName: String? = null,

    @SerializedName("strHomeLineupDefense")
    var homeDefense: String? = null,

    @SerializedName("strAwayLineupDefense")
    var awayDefense: String? = null,

    @SerializedName("strHomeLineupMidfield")
    var homeMidField: String? = null,

    @SerializedName("strAwayLineupMidfield")
    var awayMidField: String? = null,

    @SerializedName("strHomeLineupForward")
    var homeForward: String? = null,

    @SerializedName("strAwayLineupForward")
    var awayForward: String? = null,

    @SerializedName("strHomeLineupSubstitutes")
    var homeSubstitutes: String? = null,

    @SerializedName("strAwayLineupSubstitutes")
    var awaySubstitutes: String? = null
)