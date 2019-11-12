package com.coding.wk.footballapplication.utils

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


fun dateFormatter(date: String?): String? = toGMTFormatter(date, true)

fun timeFormatter(date: String?): String? = toGMTFormatter(date, false)

@SuppressLint("SimpleDateFormat")
private fun toGMTFormatter(date: String?, isDate: Boolean): String{
    val dateFormat = SimpleDateFormat(if (isDate) "yyyy-MM-dd" else getFormat(date))
    Log.d("Testing Utils", "dateFormat adalah: $dateFormat")
    val formatter  = SimpleDateFormat(if (isDate) "EEE, dd MMM yyyy" else "HH:mm")
    formatter.timeZone = TimeZone.getTimeZone("UTC")
    return formatter.format(dateFormat.parse(date))
}

@SuppressLint("SimpleDateFormat")
private fun getFormat(date: String?): String?{
    val formatLists = mapOf(
        SimpleDateFormat("HH:mm:ss") to "HH:mm:ss",
        SimpleDateFormat("HH:mm") to "HH:mm")
    for(formats in formatLists){
        try{
            formats.key.parse(date)
            return formats.value
        }
        catch (e: ParseException){
            continue
        }
    }
    return null
}


fun View.visible(){
    visibility = View.VISIBLE
}
fun View.invisible(){
    visibility = View.INVISIBLE
}