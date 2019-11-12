package com.coding.wk.footballapplication.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.coding.wk.footballapplication.R
import com.coding.wk.footballapplication.activities.detail.team.TeamDetailActivity
import com.coding.wk.footballapplication.models.team.TeamModels
import kotlinx.android.synthetic.main.grid_item_list.view.*
import org.jetbrains.anko.startActivity

class FCTeamAdapter(private val teamList: List<TeamModels>): RecyclerView.Adapter<FCTeamAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        fun bind(teamModels: TeamModels){
            Glide.with(itemView.context)
                .load(teamModels.badgeLink)
                .apply(RequestOptions().error(R.drawable.ic_error_image))
                .apply(RequestOptions().override(120, 120))
                .into(itemView.image_cutout)
            itemView.image_title.text = teamModels.teamName
            itemView.setOnClickListener {
                itemView.context.startActivity<TeamDetailActivity>(TeamDetailActivity.KEY_TEAM to teamModels)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.grid_item_list, parent, false))

    override fun getItemCount(): Int = teamList.size

    override fun onBindViewHolder(parent: ViewHolder, position: Int) =
        parent.bind(teamList[position])
}