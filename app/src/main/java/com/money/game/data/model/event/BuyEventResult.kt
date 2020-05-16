package com.money.game.data.model.event

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class BuyEventResult : Serializable{

    var id: Int = 0
    @SerializedName("user_id")
    var userId:Int = 0
    @SerializedName("category_id")
    var categoryId:Int  = 0
    @SerializedName("grand_total")
    var grandTotal:Float  = 0f
    var level:Int  = 0
    var experience:Int = 0
    @SerializedName("event_experience")
    var eventExperience:Int = 0
    @SerializedName("total_experience")
    var totalExperience:Int = 0
}