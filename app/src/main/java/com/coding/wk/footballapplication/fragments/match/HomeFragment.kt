package com.coding.wk.footballapplication.fragments.match

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.coding.wk.footballapplication.R
import com.coding.wk.footballapplication.R.id.search_home
import com.coding.wk.footballapplication.activities.search.MatchSearchActivity
import com.coding.wk.footballapplication.fragments.match.last.LastEventFragment
import com.coding.wk.footballapplication.fragments.match.next.NextEventFragment
import com.coding.wk.footballapplication.utils.FCViewTabPager
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.startActivity

class HomeFragment : Fragment(){
    private lateinit var page: Map<Fragment, String>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(
        R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        page = mutableMapOf(LastEventFragment() to resources.getString(R.string.title_tab_last_event), NextEventFragment() to resources.getString(R.string.title_tab_next_event))
        view_pager.adapter = FCViewTabPager(childFragmentManager, page)
        tab_layout.setupWithViewPager(view_pager)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.search_home, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val menuId = item.itemId
        return when(menuId){
            android.R.id.home->{
                activity?.finish()
                true
            }
            search_home->{
                context?.startActivity<MatchSearchActivity>()
                true
            }
            else->super.onOptionsItemSelected(item)
        }
    }
}
