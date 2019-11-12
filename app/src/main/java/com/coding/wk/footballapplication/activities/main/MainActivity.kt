package com.coding.wk.footballapplication.activities.main


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.coding.wk.footballapplication.R
import com.coding.wk.footballapplication.R.id.*
import com.coding.wk.footballapplication.fragments.error.ErrorFragment
import com.coding.wk.footballapplication.fragments.favorite.FavoriteFragment
import com.coding.wk.footballapplication.fragments.match.HomeFragment
import com.coding.wk.footballapplication.fragments.team.TeamFragment
import com.coding.wk.footballapplication.networks.Connection
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(Connection.isConnected(this)){
            navigation_view.setOnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    matches_menu -> {
                        if(Connection.isConnected(this)) loadSchedules(savedInstanceState) else loadError(savedInstanceState)
                    }
                    teams_menu -> {
                        if(Connection.isConnected(this)) loadTeams(savedInstanceState) else loadError(savedInstanceState)
                    }
                    favorites_menu -> {
                        if(Connection.isConnected(this)) loadFavorites(savedInstanceState) else loadError(savedInstanceState)
                    }
                    else->{
                        loadError(savedInstanceState)
                    }
                }
                true
            }
                navigation_view.selectedItemId = matches_menu
        }
        else{
            loadError(savedInstanceState)
        }
    }

    private fun loadSchedules(savedInstanceState: Bundle?){
        if (savedInstanceState == null) replaceFragment(HomeFragment())
    }

    private fun loadTeams(savedInstanceState: Bundle?){
        if (savedInstanceState == null) replaceFragment(TeamFragment())
    }
    private fun loadFavorites(savedInstanceState: Bundle?){
        if (savedInstanceState == null) replaceFragment(FavoriteFragment())
    }
    private fun loadError(savedInstanceState: Bundle?){
        if (savedInstanceState == null) replaceFragment(ErrorFragment())
    }

    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, fragment)
            .commit()
    }
}
