package com.money.game.utils

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.money.game.data.model.ApiResponse
import retrofit2.HttpException
import java.io.IOException
import java.text.DecimalFormat

class Utility {

    companion object {

        fun handleAPIError(
            e: Throwable,
            errorStream: MutableLiveData<String>,
            unAuthenticatedStream: MutableLiveData<String>
        ) {
            if (e is HttpException) {
                val gson = Gson()
                Log.e("error", "" + e.code())
                when (e.code()) {
                    401, 403 -> {
                        unAuthenticatedStream.postValue("Silahkan masuk kembali")
                        try {
                            val response = gson.fromJson(
                                e.response()!!.errorBody()!!.string(),
                                ApiResponse::class.java!!
                            )
                            errorStream.postValue(response.message)
                        } catch (ex: IllegalStateException) {
                            ex.printStackTrace()
                            errorStream.postValue(e.message)
                        } catch (ex: IOException) {
                            ex.printStackTrace()
                            errorStream.postValue(e.message)
                        } catch (ex: JsonSyntaxException) {
                            ex.printStackTrace()
                            errorStream.postValue(e.message)
                        }

                    }
                    else -> try {
                        val response = gson.fromJson(
                            e.response()!!.errorBody()!!.string(),
                            ApiResponse::class.java!!
                        )
                        errorStream.postValue(response.message)
                    } catch (ex: IllegalStateException) {
                        ex.printStackTrace()
                        errorStream.postValue(e.message)
                    } catch (ex: IOException) {
                        ex.printStackTrace()
                        errorStream.postValue(e.message)
                    } catch (ex: JsonSyntaxException) {
                        ex.printStackTrace()
                        errorStream.postValue(e.message)
                    }

                }
            } else
                errorStream.postValue(e.message)
        }
        fun currencyFormat(amount: String): String {
            val formatter = DecimalFormat("###,###,##0.00")
            return formatter.format(java.lang.Double.parseDouble(amount))
        }
    }
}