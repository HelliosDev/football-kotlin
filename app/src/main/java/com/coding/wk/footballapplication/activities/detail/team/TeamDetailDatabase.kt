package com.coding.wk.footballapplication.activities.detail.team

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import com.coding.wk.footballapplication.databases.database
import com.coding.wk.footballapplication.models.team.TeamModels
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.toast

class TeamDetailDatabase(private val context: Context) {
    fun addRowTeam(data: TeamModels){
        try {
            context.database.use {
                insert(
                    TeamModels.TABLE_FAVORITE_TEAM,
                    TeamModels.TEAM_ID to data.teamId,
                    TeamModels.TEAM_SPORT to data.teamSport,
                    TeamModels.TEAM_NAME to data.teamName,
                    TeamModels.TEAM_DESCRIPTION to data.teamDescription,
                    TeamModels.TEAM_BADGE to data.badgeLink,
                    TeamModels.TEAM_BANNER to data.teamBanner
                )
            }
        }
        catch (e: SQLiteConstraintException){
            context.toast(e.localizedMessage)
        }
    }
    fun removeRowTeam(data: TeamModels){
        try{
            context.database.use {
                delete(TeamModels.TABLE_FAVORITE_TEAM, TeamModels.TEAM_ID+"={id}","id" to data.teamId.toString())
            }
        }
        catch (e: SQLiteConstraintException){
            context.toast(e.localizedMessage)
        }

    }
}