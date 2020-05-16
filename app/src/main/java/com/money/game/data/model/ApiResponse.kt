package com.money.game.data.model

import com.google.gson.annotations.SerializedName


class ApiResponse<T> {
    var message: String? = null

    var data: T? = null

    @SerializedName("status")
    var success: Boolean = false

    override fun toString(): String {
        return "ClassPojo [message = $message, data = $data, status = $success]"
    }
}