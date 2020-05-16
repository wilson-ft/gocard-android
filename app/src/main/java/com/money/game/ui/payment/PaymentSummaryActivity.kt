package com.money.game.ui.payment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.money.game.R
import com.money.game.base.BaseActivity
import com.money.game.data.model.User.User
import com.money.game.data.model.event.BuyEventResult
import com.money.game.data.model.event.Event
import com.money.game.databinding.ActivityPaymentSummaryBinding
import com.money.game.di.util.ViewModelFactory
import com.money.game.ui.home.HomeActivityViewModel
import com.money.game.utils.SharedPrefHelper
import com.money.game.utils.Utility
import javax.inject.Inject

class PaymentSummaryActivity : BaseActivity(){


    @Inject
    lateinit  var viewModelFactory: ViewModelFactory
    internal var viewModel: PaymentSummaryViewModel?=null

    var binding: ActivityPaymentSummaryBinding?=null

    var event:Event? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment_summary)

        event = intent.getSerializableExtra("EVENT") as Event

        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(PaymentSummaryViewModel::class.java)

        viewModel?.getErrorMessage()?.observe(this, Observer { this.showSnackBar(it) })
        viewModel?.getAuthErrorMessage()?.observe(this, Observer { kickUser(it) })
        viewModel?.getLoading()?.observe(this, Observer { this.showLoading(it) })
        viewModel?.getBuyEventResult()?.observe(this, Observer { onTransferResultReturned(it) })
        viewModel?.getEventDetailResult()?.observe(this, Observer { updateInfo(it) })

        binding?.btnPay?.isEnabled= false
        binding?.etPin?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0?.length==6)
                    binding?.btnPay?.isEnabled = true
                else
                    binding?.btnPay?.isEnabled = false
            }
        })

        binding?.btnPay?.setOnClickListener({
            viewModel?.buyEvent(event?.id!!)
        })
        updateInfo(event!!)
    }

    fun updateInfo(event:Event){
        binding?.tvName?.setText(event.name)
        binding?.tvLocation?.setText(event.locatedAt)
        binding?.tvPrice?.setText("S$"+Utility.currencyFormat(""+event.price))
    }

    fun onTransferResultReturned(buyEventResult: BuyEventResult){
        var intent = Intent(this, PaymentSuccessActivity::class.java)
        intent.putExtra("RESULT", buyEventResult)
        intent.putExtra("EVENT", event)
        startActivity(intent)
        finish()
    }



}