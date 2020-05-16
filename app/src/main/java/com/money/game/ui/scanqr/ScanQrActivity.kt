package com.money.game.ui.scanqr

import android.content.Intent
import android.os.Bundle
import android.util.EventLog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.camerakit.CameraKit
import com.camerakit.CameraKitView
import com.money.game.R
import com.money.game.data.model.event.Event
import com.money.game.databinding.ActivityScanQrBinding
import com.money.game.ui.payment.PaymentSummaryActivity

class ScanQrActivity: AppCompatActivity(){
    var binding: ActivityScanQrBinding? = null
    var cameraKitView: CameraKitView? = null

    var event: Event? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_scan_qr)

        event = intent.getSerializableExtra("EVENT") as Event

        cameraKitView = binding?.camera
        cameraKitView?.clipToOutline = true
        cameraKitView?.facing = CameraKit.FACING_BACK
        cameraKitView?.focus = CameraKit.FOCUS_CONTINUOUS

        binding?.ivOverlay?.setOnClickListener({goToSummary()})

    }

    fun goToSummary(){
        var intent = Intent(this, PaymentSummaryActivity::class.java)
        intent.putExtra("EVENT", event)
        startActivity(intent)
        finish()
    }


    protected override fun onStart() {
        super.onStart()
        cameraKitView?.setPermissions(CameraKitView.PERMISSION_STORAGE or CameraKitView.PERMISSION_CAMERA)
        cameraKitView?.onStart()
    }

    protected override fun onResume() {
        super.onResume()
        cameraKitView?.onResume()
    }

    protected override fun onPause() {
        cameraKitView?.onPause()
        super.onPause()
    }

    protected override fun onStop() {
        cameraKitView?.onStop()
        super.onStop()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        cameraKitView?.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}