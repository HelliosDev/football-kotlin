package com.coding.wk.footballapplication.networks

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

object Connection {
    fun isConnected(context: Context): Boolean{
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val network = manager.activeNetwork
            if(network != null){
                val capabilities = manager.getNetworkCapabilities(network)
                return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
            }
        }
        else{
            val networkInfo = manager.activeNetworkInfo
            if(networkInfo != null){
                return ((networkInfo.type == ConnectivityManager.TYPE_WIFI || networkInfo.type == ConnectivityManager.TYPE_WIFI) && networkInfo.isConnected)
            }
        }
        return false
    }
}