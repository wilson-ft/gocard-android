package com.money.game.ui.login

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.money.game.R
import com.money.game.base.BaseActivity
import com.money.game.databinding.ActivityLoginBinding
import com.money.game.ui.faceauthentication.FaceAuthenticationActivity
import com.money.game.ui.home.HomeActivity
import com.money.game.utils.SharedPrefHelper

class LoginActivity: AppCompatActivity(), View.OnClickListener {
    var binding: ActivityLoginBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.e("asd","oncreate")

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        binding?.btnLogin?.setOnClickListener(this)

    }

    override fun onResume() {
        super.onResume()
        Log.e("asd","resume")
        if(SharedPrefHelper.isUserExists!!){
            var intent:Intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }


    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.btn_login -> goToFaceAuthentication()
        }
    }

    fun goToFaceAuthentication(){
        var intent:Intent = Intent(this, FaceAuthenticationActivity::class.java)
        intent.putExtra("PHONE", binding?.etPhone?.text.toString())
        startActivity(intent)
    }


}