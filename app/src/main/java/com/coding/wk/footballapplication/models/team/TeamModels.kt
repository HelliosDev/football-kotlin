package com.coding.wk.footballapplication.models.team

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TeamModels(
    var id: Long? = null,

    @SerializedName("idTeam")
    var teamId: String? = null,

    @SerializedName("strSport")
    var teamSport: String? = null,

    @SerializedName("strTeam")
    var teamName: String? = null,

    @SerializedName("strDescriptionEN")
    var teamDescription: String? = null,

    @SerializedName("strTeamBadge")
    var badgeLink: String? = null,

    @SerializedName("strTeamFanart1")
    var teamBanner: String? = null

): Parcelable{
    companion object {
        const val TABLE_FAVORITE_TEAM = "table_favorite_team"
        const val ID: String = "_ID"
        const val TEAM_ID: String = "team_id"
        const val TEAM_SPORT: String = "team_sport"
        const val TEAM_NAME: String = "team_name"
        const val TEAM_DESCRIPTION = "team_description"
        const val TEAM_BADGE: String = "team_badge"
        const val TEAM_BANNER: String = "team_banner"
    }
}
