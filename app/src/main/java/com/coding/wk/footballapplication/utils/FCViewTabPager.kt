package com.coding.wk.footballapplication.utils

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class FCViewTabPager(fm: FragmentManager?, private val pageFragment: Map<Fragment, String>): FragmentPagerAdapter(fm) {

    override fun getCount(): Int = pageFragment.size

    override fun getPageTitle(position: Int): String = pageFragment.values.toList()[position]

    override fun getItem(position: Int): Fragment? = pageFragment.keys.toList()[position]
}