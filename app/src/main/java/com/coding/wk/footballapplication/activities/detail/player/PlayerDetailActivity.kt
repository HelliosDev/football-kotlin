package com.coding.wk.footballapplication.activities.detail.player

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.coding.wk.footballapplication.R
import com.coding.wk.footballapplication.models.player.PlayerModels
import kotlinx.android.synthetic.main.activity_detail_player.*

class PlayerDetailActivity: AppCompatActivity() {
    private lateinit var playersList: PlayerModels
    companion object {
        const val KEY_PLAYER = "key_player"
    }
    private fun setImage(imgUrl: String?, imageView: ImageView) =
        Glide
            .with(applicationContext)
            .load(imgUrl)
            .apply(RequestOptions().error(R.drawable.ic_error_image))
            .into(imageView)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_player)

        playersList = intent.getParcelableExtra(KEY_PLAYER)
        setImage(playersList.playerBack, back_detail_player)
        tv_weight.text = playersList.playerWeight
        tv_height.text = playersList.playerHeight
        tv_position.text = playersList.playerPosition
        tv_description.text = playersList.playerDescription
    }
}