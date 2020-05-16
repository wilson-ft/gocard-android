package com.money.game.base

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.money.game.R
import com.money.game.ui.dialog.LoadingDialog
import com.money.game.utils.SharedPrefHelper
import dagger.android.support.DaggerAppCompatActivity
import android.R.id
import android.widget.TextView
import android.R.id.message
import android.graphics.Color
import com.money.game.ui.login.LoginActivity


open class BaseActivity: DaggerAppCompatActivity() {

    var loadingDialog: LoadingDialog = LoadingDialog()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    public fun showLoading(isShowing: Boolean){
        if (isShowing) {
            if (!loadingDialog.isVisible())
                loadingDialog.show(supportFragmentManager, "LOADING")

        } else {
            if (loadingDialog.isVisible())
                loadingDialog.dismiss()
        }

    }

    fun showSnackBar(message: String) {
        val root:View = findViewById(com.money.game.R.id.content)
        val snack = Snackbar.make(root, message, Snackbar.LENGTH_LONG)
        val view = snack.getView()
        val tv = view.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        tv.setTextColor(Color.WHITE)
        snack.show()
    }


    fun kickUser(message: String) {
        SharedPrefHelper.clearUserData()
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        var i = Intent(this, LoginActivity::class.java)
        startActivity(i)
        finish()

    }


}