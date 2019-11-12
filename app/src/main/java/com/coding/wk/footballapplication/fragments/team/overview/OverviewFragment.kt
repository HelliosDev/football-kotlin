package com.coding.wk.footballapplication.fragments.team.overview

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.coding.wk.footballapplication.R
import kotlinx.android.synthetic.main.fragment_overview.*

class OverviewFragment: Fragment() {
    companion object {
        private const val KEY_OVERVIEW = "key_overview"
        fun newInstance(values: String?): OverviewFragment{
            val overviewFragment = OverviewFragment()
            val args = Bundle()
            args.putString(KEY_OVERVIEW, values)
            overviewFragment.arguments = args
            return overviewFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_overview, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val overviewText = arguments?.getString(KEY_OVERVIEW)
        text_overview.text = overviewText
    }
}