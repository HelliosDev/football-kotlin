package com.coding.wk.footballapplication.activities.detail.team

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.coding.wk.footballapplication.R
import com.coding.wk.footballapplication.databases.database
import com.coding.wk.footballapplication.fragments.team.allplayer.AllPlayerFragment
import com.coding.wk.footballapplication.fragments.team.overview.OverviewFragment
import com.coding.wk.footballapplication.models.team.TeamModels
import com.coding.wk.footballapplication.utils.FCViewTabPager
import kotlinx.android.synthetic.main.activity_detail_team.*
import kotlinx.android.synthetic.main.layout_detail.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class TeamDetailActivity: AppCompatActivity(){
    private var isFavorites: Boolean = false
    private lateinit var helper: TeamDetailDatabase
    private lateinit var teamLists: TeamModels
    private var menuFavorite: Menu? = null
    private lateinit var page: Map<Fragment, String>
    companion object {
        const val KEY_TEAM = "key_team"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_team)

        teamLists= intent.getParcelableExtra(KEY_TEAM)
        val teamId = teamLists.teamId
        val teamDescription = teamLists.teamDescription

        Glide.with(this)
            .load(teamLists.teamBanner)
            .apply(RequestOptions().error(R.drawable.ic_error_image))
            .into(team_banner)

        helper = TeamDetailDatabase(this)

        page = mutableMapOf(OverviewFragment.newInstance(teamDescription) to getString(R.string.overview_tab_title), AllPlayerFragment.newInstance(teamId) to getString(R.string.player_tab_title))
        detail_view_pager.adapter = FCViewTabPager(supportFragmentManager, page)
        detail_tab.setupWithViewPager(detail_view_pager)

        checkFavorite()
    }

    private fun addFavorites(){
        helper.addRowTeam(teamLists)
    }
    private fun removeFavorites(){
        helper.removeRowTeam(teamLists)
    }
    private fun checkFavorite(){
        database.use{
            val rowResult = select(TeamModels.TABLE_FAVORITE_TEAM).whereArgs(TeamModels.TEAM_ID+" ={id}", "id" to teamLists.teamId.toString())
                .parseList(classParser<TeamModels>())
            if(rowResult.isNotEmpty()) isFavorites = true
        }
    }
    private fun setFavorites(){
        if(!isFavorites) setIcon(R.drawable.ic_add) else setIcon(R.drawable.ic_added)
    }
    private fun setIcon(resourceId: Int){
        menuFavorite?.getItem(0)?.icon = ContextCompat.getDrawable(this, resourceId)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.favorite_menu, menu)
        menuFavorite = menu
        setFavorites()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val menuId = item.itemId
        return when(menuId){
            android.R.id.home->{
                finish()
                true
            }
            R.id.add_favorite ->{
                if (!isFavorites) addFavorites() else removeFavorites()
                isFavorites = !isFavorites
                setFavorites()
                true
            }
            else->super.onOptionsItemSelected(item)
        }
    }
}