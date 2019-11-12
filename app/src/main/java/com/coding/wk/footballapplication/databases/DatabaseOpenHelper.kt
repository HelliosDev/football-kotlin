package com.coding.wk.footballapplication.databases

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.coding.wk.footballapplication.models.match.MatchModels
import com.coding.wk.footballapplication.models.team.TeamModels
import org.jetbrains.anko.db.*

class DatabaseOpenHelper(context: Context): ManagedSQLiteOpenHelper(context, "FavoriteDB.db", null, 1) {
    companion object {
        private var instance: DatabaseOpenHelper? = null
        @Synchronized
        fun getInstance(context: Context): DatabaseOpenHelper {
            if(instance == null) instance = DatabaseOpenHelper(context.applicationContext)
            return instance as DatabaseOpenHelper
        }
    }
    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(
            MatchModels.TABLE_FAVORITE_MATCH, true,
            MatchModels.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            MatchModels.EVENT_ID to TEXT + UNIQUE,
            MatchModels.HOME_ID to TEXT,
            MatchModels.AWAY_ID to TEXT,
            MatchModels.EVENT_LEAGUE to TEXT,
            MatchModels.EVENT_NAME to TEXT,
            MatchModels.EVENT_DATE to TEXT,
            MatchModels.EVENT_TIME to TEXT,
            MatchModels.HOME_NAME to TEXT,
            MatchModels.AWAY_NAME to TEXT,
            MatchModels.HOME_SCORE to TEXT,
            MatchModels.AWAY_SCORE to TEXT)

        db.createTable(
            TeamModels.TABLE_FAVORITE_TEAM, true,
            TeamModels.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            TeamModels.TEAM_ID to TEXT + UNIQUE,
            TeamModels.TEAM_SPORT to TEXT,
            TeamModels.TEAM_NAME to TEXT,
            TeamModels.TEAM_DESCRIPTION to TEXT,
            TeamModels.TEAM_BADGE to TEXT,
            TeamModels.TEAM_BANNER to TEXT)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(MatchModels.TABLE_FAVORITE_MATCH, true)
        db.dropTable(TeamModels.TABLE_FAVORITE_TEAM, true)
    }
}
val Context.database: DatabaseOpenHelper
get() = DatabaseOpenHelper.getInstance(applicationContext)