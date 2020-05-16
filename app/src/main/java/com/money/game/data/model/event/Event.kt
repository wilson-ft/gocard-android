package com.money.game.data.model.event

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Event : Serializable{

    var id: Int = 0
    @SerializedName("category_id")
    var categoryId: Int = 0
    var name: String = ""
    var address: String = ""
    var price: Float = 0f
    var experience: Int = 0
    var label: String = ""
    var cashback: Float = 0f
    @SerializedName("located_at")
    var locatedAt: String = ""
    @SerializedName("open_at")
    var openAt: String = ""
    @SerializedName("closed_at")
    var closedAt: String = ""
}