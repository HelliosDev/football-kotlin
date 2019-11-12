package com.coding.wk.footballapplication.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.coding.wk.footballapplication.R
import com.coding.wk.footballapplication.activities.detail.player.PlayerDetailActivity
import com.coding.wk.footballapplication.models.player.PlayerModels
import kotlinx.android.synthetic.main.grid_item_list.view.*
import org.jetbrains.anko.startActivity

class FCPlayerAdapter(private val playerList: List<PlayerModels>): RecyclerView.Adapter<FCPlayerAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        fun bind(playerModels: PlayerModels){
            Glide.with(itemView.context)
                .load(playerModels.playerProfile)
                .apply(RequestOptions().error(R.drawable.ic_error_player))
                .apply(RequestOptions().override(120, 120))
                .into(itemView.image_cutout)
            itemView.image_title.text = playerModels.playerName
            itemView.setOnClickListener{
                itemView.context.startActivity<PlayerDetailActivity>(PlayerDetailActivity.KEY_PLAYER to playerModels)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.grid_item_list, parent, false))

    override fun getItemCount(): Int = playerList.size

    override fun onBindViewHolder(parent: ViewHolder, position: Int) =
        parent.bind(playerList[position])
}