package com.coding.wk.footballapplication.fragments.team.allplayer

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.coding.wk.footballapplication.R
import com.coding.wk.footballapplication.adapters.FCPlayerAdapter
import com.coding.wk.footballapplication.models.player.PlayerModels
import com.coding.wk.footballapplication.networks.ApiRepository
import com.coding.wk.footballapplication.utils.invisible
import com.coding.wk.footballapplication.utils.visible
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_player_all.*

class AllPlayerFragment: Fragment(),AllPlayerView {
    private lateinit var presenter: AllPlayerPresenter
    private lateinit var adapter: FCPlayerAdapter
    private var playerLists: MutableList<PlayerModels> = mutableListOf()

    companion object {
        private const val KEY_ALL_PLAYER = "key_all_player"
        fun newInstance(values: String?): AllPlayerFragment{
            val allPlayerFragment = AllPlayerFragment()
            val args = Bundle()
            args.putString(KEY_ALL_PLAYER, values)
            allPlayerFragment.arguments = args
            return allPlayerFragment
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_player_all, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val teamId = arguments?.getString(KEY_ALL_PLAYER)
        presenter = AllPlayerPresenter(this, ApiRepository(), Gson())
        adapter = FCPlayerAdapter(playerLists)
        rv_all_player.layoutManager = GridLayoutManager(context , 3)
        rv_all_player.adapter = adapter
        presenter.getPlayerAll(teamId)
    }

    override fun showLoading() = all_player_pb.visible()

    override fun hideLoading() = all_player_pb.invisible()

    override fun showAllPlayer(data: List<PlayerModels>) {
        playerLists.clear()
        playerLists.addAll(data)
        adapter.notifyDataSetChanged()
    }
}