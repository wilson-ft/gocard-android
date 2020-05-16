package com.money.game.ui.payment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.money.game.R
import com.money.game.databinding.ActivityPaymentSuccessBinding

class PaymentSuccessActivity:AppCompatActivity(){

    var binding: ActivityPaymentSuccessBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment_success)

        binding?.btnDone?.setOnClickListener({finish()})
    }
}