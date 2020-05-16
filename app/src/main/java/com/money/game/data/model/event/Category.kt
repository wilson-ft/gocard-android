package com.money.game.data.model.event

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Category : Serializable{

    @SerializedName("category_id")
    var categoryId:Int = 0
    var name: String=""
    var level:Int = 0
    var experience:Int = 0
    @SerializedName("total_experience")
    var totalExperience:Int = 0
}