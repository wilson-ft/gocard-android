package com.money.game.data.model.User

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class LoginInput:Serializable {

    var username: String? = null

    var password: String? = null

    @SerializedName("device_id")
    var deviceId: String? = null
}
