package com.money.game.ui.map

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.money.game.R
import com.money.game.base.BaseFragment
import com.money.game.data.model.event.Category
import com.money.game.data.model.event.Event
import com.money.game.databinding.FragmentMapBinding
import com.money.game.di.util.ViewModelFactory
import com.money.game.ui.home.HomeActivityViewModel
import com.money.game.ui.scanqr.ScanQrActivity
import javax.inject.Inject

class MapFragment : BaseFragment(){

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    internal var viewModel: HomeActivityViewModel? =null

    var binding: FragmentMapBinding? = null

    var event:Event? = null

    var category:Category = Category()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =
            ViewModelProviders.of(activity!!, viewModelFactory).get(HomeActivityViewModel::class.java)
        viewModel?.getErrorMessage()?.observe(this, Observer { this.showSnackBar(it) })
        viewModel?.getAuthErrorMessage()?.observe(this, Observer { kickUser(it) })
        viewModel?.getLoading()?.observe(this, Observer { this.showLoading(it) })
        viewModel?.getEventDetailResult()?.observe(this, Observer { onEventDetailReturned(it) })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var v: View = inflater.inflate(R.layout.fragment_map, container, false)
        binding = DataBindingUtil.bind(v)

        binding?.ivBack?.setOnClickListener({ activity?.onBackPressed() })
        binding?.ivMcd?.setOnClickListener({viewModel?.getEventDetail(1)})
        binding?.ivBk?.setOnClickListener({viewModel?.getEventDetail(2)})

        refreshInfo()
        return v
    }

    fun onEventDetailReturned(event: Event){
        this.event = event
        showBuyDialog()
    }

    fun setSelectedCategory(category: Category){
        this.category = category
        refreshInfo()
    }

    fun refreshInfo(){

        binding?.tvLevel?.setText("Level "+category.level)
        var exp = category.experience
        if(exp==0)
            exp = 50
        binding?.tvExp?.setText("Exp "+exp+"/50")

    }

    fun showBuyDialog(){

        val dialog: Dialog
        dialog = Dialog(this.context!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(
            ContextCompat.getDrawable(
                this.context!!,
                R.drawable.bg_dialog
            )
        )
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_buy)
        val ivLogo = dialog.findViewById(R.id.iv_logo) as ImageView
        val btnPay = dialog.findViewById(R.id.btn_pay) as Button
        val tvName = dialog.findViewById(R.id.tv_name) as TextView
        val tvLabel = dialog.findViewById(R.id.tv_label) as TextView
        val tvAddress = dialog.findViewById(R.id.tv_address) as TextView
        val tvLocation = dialog.findViewById(R.id.tv_location) as TextView

        if(event?.id==1)
            ivLogo.setImageDrawable(ContextCompat.getDrawable(context!!, R.drawable.logo_mcd))
        else
            ivLogo.setImageDrawable(ContextCompat.getDrawable(context!!, R.drawable.logo_bk))


        tvName.setText(event?.name)
        tvLabel.setText(event?.label)
        tvAddress.setText(event?.address)
        tvLocation.setText(event?.locatedAt)

        btnPay.setOnClickListener({
            goToScanQR()
            dialog.dismiss()
        })

        dialog.show()
    }

    fun goToScanQR(){
        val intent = Intent(context, ScanQrActivity::class.java)
        intent.putExtra("EVENT", event)
        startActivity(intent)
    }
}