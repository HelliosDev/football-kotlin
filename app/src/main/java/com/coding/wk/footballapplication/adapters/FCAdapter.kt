package com.coding.wk.footballapplication.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.provider.CalendarContract
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.coding.wk.footballapplication.R
import com.coding.wk.footballapplication.activities.detail.match.MatchDetailActivity
import com.coding.wk.footballapplication.models.match.MatchModels
import com.coding.wk.footballapplication.utils.dateFormatter
import com.coding.wk.footballapplication.utils.timeFormatter
import com.coding.wk.footballapplication.utils.visible
import kotlinx.android.synthetic.main.match_item_list.view.*
import org.jetbrains.anko.startActivity
import java.text.SimpleDateFormat

class FCAdapter(private val matchLists: List<MatchModels>, private val isReminder: Boolean = false): RecyclerView.Adapter<FCAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        fun bind(matchModels: MatchModels, isReminder: Boolean){
            val newDate = if(matchModels.eventDate != null) dateFormatter(matchModels.eventDate) else "No Date"
            val newTime = if(matchModels.eventTime != null) timeFormatter(matchModels.eventTime) else "No Time"
            itemView.date_match.text =  newDate
            itemView.time_match.text = newTime
            itemView.home_fc_name.text = if(matchModels.homeName != null) matchModels.homeName else "No Home"
            itemView.away_fc_name.text = if(matchModels.awayName != null) matchModels.awayName else "No Away"
            itemView.home_score.text = matchModels.homeScore
            itemView.away_score.text = matchModels.awayScore
            itemView.setOnClickListener{
                itemView.context.startActivity<MatchDetailActivity>(MatchDetailActivity.KEY_MATCH to matchModels)
            }
            if(isReminder){
                itemView.btn_scheduler.visible()
                itemView.btn_scheduler.setOnClickListener {
                    val intent = Intent(Intent.ACTION_EDIT)
                    intent.data = CalendarContract.Events.CONTENT_URI
                    intent.putExtra(CalendarContract.Events.TITLE, matchModels.eventName)
                    intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, getMillis(newDate, newTime))
                    intent.putExtra(CalendarContract.Events.ALL_DAY, false)
                    itemView.context.startActivity(intent)
                }
            }
        }
        @SuppressLint("SimpleDateFormat")
        fun getMillis(date: String?, time: String?): Long{
            var millis = 0L
            val dateFormat = SimpleDateFormat("E, dd MMM yyyy HH:mm")
            try{
                val dateTime = "$date $time"
                val newFormat = dateFormat.parse(dateTime)
                millis = newFormat.time
            }
            catch (e: Exception){
                e.printStackTrace()
            }
            return millis
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder = ViewHolder(LayoutInflater.from(parent.context).inflate(
        R.layout.match_item_list, parent, false))

    override fun getItemCount(): Int = matchLists.size

    override fun onBindViewHolder(parent: ViewHolder, position: Int) =
       parent.bind(matchLists[position], isReminder)
}