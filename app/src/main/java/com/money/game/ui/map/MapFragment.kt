package com.money.game.ui.map

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.money.game.R
import com.money.game.databinding.FragmentMapBinding
import com.money.game.ui.scanqr.ScanQrActivity

class MapFragment : Fragment(){

    var binding: FragmentMapBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var v: View = inflater.inflate(R.layout.fragment_map, container, false)
        binding = DataBindingUtil.bind(v)

        binding?.ivBack?.setOnClickListener({ activity?.onBackPressed() })
        binding?.ivMcd?.setOnClickListener({showBuyDialog()})
        return v
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
        val btnPay = dialog.findViewById(R.id.btn_pay) as Button
        btnPay.setOnClickListener({
            goToScanQR()
            dialog.dismiss()
        })

        dialog.show()
    }

    fun goToScanQR(){
        val intent = Intent(context, ScanQrActivity::class.java)
        startActivity(intent)
    }
}