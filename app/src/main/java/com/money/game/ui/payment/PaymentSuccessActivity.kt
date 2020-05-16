package com.money.game.ui.payment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.money.game.R
import com.money.game.data.model.event.BuyEventResult
import com.money.game.data.model.event.Event
import com.money.game.databinding.ActivityPaymentSuccessBinding
import com.money.game.utils.Utility

class PaymentSuccessActivity:AppCompatActivity(){

    var binding: ActivityPaymentSuccessBinding? = null

    var buyResult: BuyEventResult? = null
    var event: Event? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment_success)

        buyResult = intent.getSerializableExtra("RESULT") as BuyEventResult
        event = intent.getSerializableExtra("EVENT") as Event

        binding?.btnDone?.setOnClickListener({finish()})

        binding?.tvPrice?.setText("S$"+Utility.currencyFormat(""+buyResult?.grandTotal))
        binding?.tvCashback?.setText(event?.label)
        binding?.tvExp?.setText("Exp "+buyResult?.experience)
        binding?.tvAddExp?.setText("+"+buyResult?.eventExperience)
        binding?.tvExpLabel?.setText(""+buyResult?.eventExperience+" Experience")
        binding?.tvLevel?.setText("Level "+buyResult?.level)

        var exp:Int  =  buyResult?.experience!!.rem(50)
        if(exp==0)
            exp=50
        binding?.pbExp?.progress = exp*100/50
    }
}