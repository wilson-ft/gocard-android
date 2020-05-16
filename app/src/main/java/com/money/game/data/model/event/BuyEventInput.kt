package com.money.game.data.model.event

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class BuyEventInput : Serializable{

    @SerializedName("event_id")
    var eventId: Int = 0
}