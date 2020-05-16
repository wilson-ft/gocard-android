package com.money.game.base

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.money.game.ui.dialog.LoadingDialog
import com.money.game.ui.login.LoginActivity
import com.money.game.utils.SharedPrefHelper
import dagger.android.support.DaggerFragment


open class BaseFragment : DaggerFragment() {

    var loadingDialog: LoadingDialog = LoadingDialog()

    internal var root: View? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        root = view
    }

    fun showLoading(isShowing: Boolean) {
        Log.e("LOADING", "asd $isShowing")
        if (isShowing) {
            if (!loadingDialog.isAdded && !loadingDialog?.isVisible())
                fragmentManager?.let { loadingDialog?.show(it, "LOADING")}
        } else {
            if (loadingDialog?.isVisible())
                loadingDialog?.dismiss()
        }
    }

    fun showSnackBar(message: String) {
        root?.let { Snackbar.make(it, message, Snackbar.LENGTH_LONG).show() }
    }

    fun kickUser(message: String) {
        SharedPrefHelper.clearUserData()
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        var i = Intent(context, LoginActivity::class.java)
        startActivity(i)
        activity?.finish()
    }
}
