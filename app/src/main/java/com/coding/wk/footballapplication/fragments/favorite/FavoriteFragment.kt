package com.coding.wk.footballapplication.fragments.favorite

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.coding.wk.footballapplication.R
import com.coding.wk.footballapplication.utils.FCViewTabPager
import kotlinx.android.synthetic.main.fragment_favorite.*

class FavoriteFragment: Fragment() {
    private lateinit var page: Map<Fragment, String>
    private fun setTitle(resource: Int) = resources.getString(resource)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_favorite, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        page = mutableMapOf(FavoriteMatchFragment() to setTitle(R.string.title_tab_fav_match), FavoriteTeamFragment() to setTitle(R.string.title_tab_fav_team))
        fav_view_pager.adapter = FCViewTabPager(childFragmentManager, page)
        fav_tab_layout.setupWithViewPager(fav_view_pager)
    }
}