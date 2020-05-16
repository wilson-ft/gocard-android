package com.money.game.utils

import android.content.Context
import android.util.Log
import com.money.game.data.model.User.User


object SharedPrefHelper {

    private var context: Context? = null
    private val NAME = "HOOD"

    private val KEY_ID = "ID"
    private val KEY_TOKEN = "TOKEN"
    private val KEY_USERNAME = "USERNAME"
    private val KEY_FIRST_NAME = "FIRST_NAME"
    private val KEY_LAST_NAME = "LAST_NAME"
    private val KEY_BALANCE = "BALANCE"
    private val KEY_ROLE = "ROLE"
    private val KEY_PHONE = "PHONE"
    private val KEY_ADDRESS = "ADDRESS"

    private val KEY_FCM_ID = "FCM_ID"
    private val KEY_IS_NEW_FCM_ID = "IS_NEW_FCM_ID"

    private val KEY_IS_ALERT_ON = "IS_ALERT_ON"

    val isUserExists: Boolean?
        get() = context!!.getSharedPreferences(NAME, Context.MODE_PRIVATE).contains(KEY_ID)

    val userID: Int?
        get() = context!!.getSharedPreferences(NAME, Context.MODE_PRIVATE).getInt(KEY_ID, -1)

    val apiToken: String?
        get() = context!!.getSharedPreferences(NAME, Context.MODE_PRIVATE).getString(KEY_TOKEN, "")

    val userName: String?
        get() = context!!.getSharedPreferences(NAME, Context.MODE_PRIVATE).getString(
            KEY_USERNAME,
            ""
        )

    val balance: Float?
        get() = context!!.getSharedPreferences(NAME, Context.MODE_PRIVATE).getFloat(KEY_BALANCE, 0f)

    val firstName: String?
        get() = context!!.getSharedPreferences(NAME, Context.MODE_PRIVATE).getString(KEY_FIRST_NAME, "")

    val lastName: String?
        get() = context!!.getSharedPreferences(NAME, Context.MODE_PRIVATE).getString(KEY_LAST_NAME, "")

    val phone: String?
        get() = context!!.getSharedPreferences(NAME, Context.MODE_PRIVATE).getString(KEY_PHONE, "")

    val address: String?
        get() = context!!.getSharedPreferences(NAME, Context.MODE_PRIVATE).getString(
            KEY_ADDRESS,
            ""
        )

    val role: String?
        get() = context!!.getSharedPreferences(NAME, Context.MODE_PRIVATE).getString(KEY_ROLE, "")

    val fcmId: String?
        get() = context!!.getSharedPreferences(NAME, Context.MODE_PRIVATE).getString(KEY_FCM_ID, "")


    var isNewFCMId: Boolean
        get() =
            context!!.getSharedPreferences(NAME, Context.MODE_PRIVATE).getBoolean(
                KEY_IS_NEW_FCM_ID,
                false
            )
        set(isNew) {
            val editor = context!!.getSharedPreferences(NAME, Context.MODE_PRIVATE).edit()
            editor.putBoolean(KEY_IS_NEW_FCM_ID, isNew)
            editor.commit()
        }


    var isAlertOn: Boolean
        get() = context!!.getSharedPreferences(NAME, Context.MODE_PRIVATE).getBoolean(
            KEY_IS_ALERT_ON,
            true
        )
        set(isAlertOn) {
            val editor = context!!.getSharedPreferences(NAME, Context.MODE_PRIVATE).edit()
            editor.putBoolean(KEY_IS_ALERT_ON, isAlertOn)
            editor.commit()
        }


    fun setContext(cont: Context) {
        context = cont
    }

    fun saveUser(user: User) {
        val editor = context!!.getSharedPreferences(NAME, Context.MODE_PRIVATE).edit()
        editor.putInt(KEY_ID, user.id)
        editor.putString(KEY_FIRST_NAME, user.firstName)
        editor.putString(KEY_LAST_NAME, user.lastName)
        editor.putString(KEY_TOKEN, user.apiToken)
        editor.putString(KEY_PHONE, user.phoneNo)
        editor.commit()
    }

    fun updateUser(user: User) {
        val editor = context!!.getSharedPreferences(NAME, Context.MODE_PRIVATE).edit()
        editor.putInt(KEY_ID, user.id)
        editor.putString(KEY_FIRST_NAME, user.firstName)
        editor.putString(KEY_LAST_NAME, user.lastName)
        if(user.apiToken != null && !user.apiToken.isEmpty())
            editor.putString(KEY_TOKEN, user.apiToken)
        editor.putString(KEY_PHONE, user.phoneNo)
        editor.putFloat(KEY_BALANCE, user.balance.toFloat())
        Log.e("okhttp","balance "+ user.balance)
        editor.commit()
    }

    fun clearUserData() {
        context!!.getSharedPreferences(NAME, Context.MODE_PRIVATE).edit().clear().commit()
    }

    fun saveFCMId(id: String) {
        val editor = context!!.getSharedPreferences(NAME, Context.MODE_PRIVATE).edit()
        editor.putString(KEY_FCM_ID, id)
        editor.putBoolean(KEY_IS_NEW_FCM_ID, true)
        editor.commit()

    }

}
