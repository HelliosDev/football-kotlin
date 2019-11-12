package com.coding.wk.footballapplication.models.player

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PlayerModels(
    @SerializedName("idPlayer")
    var playerId: String? = null,

    @SerializedName("strPlayer")
    var playerName: String? = null,

    @SerializedName("strWeight")
    var playerWeight: String? = null,

    @SerializedName("strHeight")
    var playerHeight: String? = null,

    @SerializedName("strPosition")
    var playerPosition: String? = null,

    @SerializedName("strDescriptionEN")
    var playerDescription: String? = null,

    @SerializedName("strCutout")
    var playerProfile: String? = null,

    @SerializedName("strFanart1")
    var playerBack: String? = null
):Parcelable