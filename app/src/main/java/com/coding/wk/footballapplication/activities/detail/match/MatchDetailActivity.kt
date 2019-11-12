package com.coding.wk.footballapplication.activities.detail.match

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.coding.wk.footballapplication.R
import com.coding.wk.footballapplication.R.drawable.ic_add
import com.coding.wk.footballapplication.R.drawable.ic_added
import com.coding.wk.footballapplication.R.id.add_favorite
import com.coding.wk.footballapplication.R.menu.favorite_menu
import com.coding.wk.footballapplication.databases.database
import com.coding.wk.footballapplication.models.event.EventModels
import com.coding.wk.footballapplication.models.match.MatchModels
import com.coding.wk.footballapplication.models.team.TeamModels
import com.coding.wk.footballapplication.networks.ApiRepository
import com.coding.wk.footballapplication.utils.dateFormatter
import com.coding.wk.footballapplication.utils.invisible
import com.coding.wk.footballapplication.utils.timeFormatter
import com.coding.wk.footballapplication.utils.visible
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_detail_match.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class MatchDetailActivity : AppCompatActivity(), MatchDetailView {
    private var isFavorites: Boolean = false
    private lateinit var presenter: MatchDetailPresenter
    private lateinit var itemList: MatchModels
    private var menuFavorite: Menu? = null
    companion object {
        const val KEY_MATCH = "key_match"
    }

    private fun setLogo(imgUrl: String?, imageView: ImageView) =
        Glide
            .with(applicationContext)
            .load(imgUrl)
            .apply(RequestOptions().error(R.drawable.ic_error_image))
            .apply(RequestOptions().override(100, 100))
            .into(imageView)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_match)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        itemList = intent.getParcelableExtra(KEY_MATCH)
        presenter = MatchDetailPresenter(this, ApiRepository(), Gson(), this)
        val homeId = if(itemList.homeId != null) itemList.homeId else " "
        val awayId = if(itemList.awayId != null) itemList.awayId else " "
        presenter.getDetailTeam(homeId, awayId)
        presenter.getDetailEvent(itemList.eventId)
        detail_date.text = if(itemList.eventDate != null) dateFormatter(itemList.eventDate) else "No Date"
        detail_time.text = if(itemList.eventTime != null) timeFormatter(itemList.eventTime) else "No Time"
        detail_home_name.text = if(itemList.homeName != null) itemList.homeName else "No Home"
        detail_away_name.text = if(itemList.awayName != null) itemList.awayName else "No Away"
        detail_home_score.text = itemList.homeScore
        detail_away_score.text = itemList.awayScore

        checkFavorite()
    }

    override fun showLoading() = detail_progress_bar.visible()

    override fun hideLoading() = detail_progress_bar.invisible()

    override fun showDetailTeam(dataHome: List<TeamModels>, dataAway: List<TeamModels>) {
        setLogo(dataHome[0].badgeLink, home_logo)
        setLogo(dataAway[0].badgeLink, away_logo)
    }
    override fun showDetailEvent(data: List<EventModels>) {
        goal_home.text = data[0].homeGoalPlayer
        goal_away.text = data[0].awayGoalPlayer
        home_shot.text = data[0].homeShots
        away_shot.text = data[0].awayShots
        goalkeeper_home.text = data[0].homeGoalPlayer
        goalkeeper_away.text = data[0].awayGoalPlayer
        defense_home.text = data[0].homeDefense
        defense_away.text = data[0].awayDefense
        midfield_home.text = data[0].homeMidField
        midfield_away.text = data[0].awayMidField
        forward_home.text = data[0].homeForward
        forward_away.text = data[0].awayForward
        substitutes_home.text = data[0].homeSubstitutes
        substitutes_away.text = data[0].awaySubstitutes
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(favorite_menu, menu)
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
            add_favorite->{
                if (!isFavorites) addFavorites() else removeFavorites()
                isFavorites = !isFavorites
                setFavorites()
                true
            }
            else->super.onOptionsItemSelected(item)
        }
    }
    private fun addFavorites(){
        presenter.addRowMatch(itemList)
    }
    private fun removeFavorites(){
        presenter.removeRowMatch(itemList)
    }
    private fun setFavorites(){
        if(!isFavorites) setIcon(ic_add) else setIcon(ic_added)
    }
    private fun setIcon(resourceId: Int){
        menuFavorite?.getItem(0)?.icon = ContextCompat.getDrawable(this, resourceId)
    }
    private fun checkFavorite(){
        database.use{
            val rowResult = select(MatchModels.TABLE_FAVORITE_MATCH).whereArgs(MatchModels.EVENT_ID+" ={id}", "id" to itemList.eventId.toString())
                .parseList(classParser<MatchModels>())
            if(rowResult.isNotEmpty()) isFavorites = true
        }
    }
}