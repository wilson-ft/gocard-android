package com.money.game.data.model.User

import com.google.gson.annotations.SerializedName
import com.money.game.data.model.event.Category
import java.io.Serializable

class User() : Serializable{
    var id: Int = 0

    @SerializedName("first_name")
    var firstName: String = ""

    @SerializedName("last_name")
    var lastName: String = ""

    @SerializedName("phone_no")
    var phoneNo: String = ""

    @SerializedName("api_token")
    var apiToken: String = ""

    var balance: Double = 0.0

    var categories: List<Category> = ArrayList()

}