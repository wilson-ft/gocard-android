package com.money.game.ui.faceauthentication

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.camerakit.CameraKit
import com.camerakit.CameraKitView
import com.money.game.base.BaseActivity
import com.money.game.databinding.ActivityFaceRecognitionBinding
import com.money.game.di.util.ViewModelFactory
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import android.util.DisplayMetrics
import android.graphics.Bitmap
import android.graphics.Matrix
import android.os.Handler
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.money.game.R
import com.money.game.data.model.User.User
import com.money.game.utils.SharedPrefHelper


class FaceAuthenticationActivity : BaseActivity(){


    enum class State {
        STATE_CAPTURE, STATE_PREVIEW
    };

    @Inject
    lateinit  var viewModelFactory: ViewModelFactory
    internal var viewModel: FaceAuthenticationActivityViewModel?=null

    var binding: ActivityFaceRecognitionBinding? = null

    var state:State = State.STATE_CAPTURE

    internal var savedPhoto: File? = null

    private var cameraKitView: CameraKitView? = null

    var phone: String= ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_face_recognition)

        phone = intent.getStringExtra("PHONE");

        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(FaceAuthenticationActivityViewModel::class.java!!)


        viewModel?.getErrorMessage()?.observe(this, Observer {

            changeState(State.STATE_CAPTURE)

            if(it.toLowerCase().equals("user is not verified")){
                showSnackBar("Face not recognized, please try again")
            }else
                showSnackBar(it)
        })
        viewModel?.getLoading()?.observe(this, Observer { showLoading(it) })
        viewModel?.getLoginResult()?.observe(this, Observer { onLoginSuccess(it) })

        cameraKitView = binding?.camera
        cameraKitView?.clipToOutline = true
        cameraKitView?.facing = CameraKit.FACING_FRONT
        cameraKitView?.focus = CameraKit.FOCUS_CONTINUOUS


        binding?.ivPreview?.clipToOutline = true
        binding?.ivPreview?.visibility = View.INVISIBLE

        binding?.btnCapture?.setOnClickListener(View.OnClickListener {
            cameraKitView?.captureImage(imageCallback)
        })


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

    internal var imageCallback: CameraKitView.ImageCallback =
        CameraKitView.ImageCallback { cameraKitView, bytes ->
            Log.e("asd", "onImage")
            savedPhoto = File(
                this@FaceAuthenticationActivity.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                "photo.jpg"
            )
            try {
                val outputStream = FileOutputStream(savedPhoto!!.getPath())
                outputStream.write(bytes)
                outputStream.close()
            } catch (e: java.io.IOException) {
                e.printStackTrace()
            }

            var image =
                BitmapDrawable(resources, BitmapFactory.decodeByteArray(bytes, 0, bytes.size))

            image = flip(image)

            val bitmap = cropBitmapCenter(image.bitmap, cameraKitView.width, cameraKitView.height)

            binding?.ivPreview?.setImageBitmap(bitmap)
            changeState(State.STATE_PREVIEW)
        }

    fun changeState(state:State){
        this.state = state

        when(state){
            State.STATE_CAPTURE -> {
                binding?.ivPreview?.visibility = View.INVISIBLE
                binding?.btnCapture?.visibility = View.VISIBLE
            }
            State.STATE_PREVIEW -> {
                binding?.ivPreview?.visibility = View.VISIBLE
                binding?.btnCapture?.visibility = View.INVISIBLE
                savedPhoto?.let { viewModel?.doLogin(phone, it) }
            }
        }
    }

    fun onLoginSuccess(user: User){
        Log.e("asd",user.apiToken)
        binding?.tvInstruction?.setText("Verification passed")
        binding?.tvInstruction?.setTextColor(ContextCompat.getColor(this, R.color.success))
        binding?.ivDecor?.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.bg_preview_decor_success))
        SharedPrefHelper.saveUser(user)

        Handler().postDelayed(Runnable {
            finish()
        }, 2000)
    }

    fun flip(d: BitmapDrawable): BitmapDrawable {
        val m = Matrix()
        m.preScale(-1f, 1f)
        val src = d.bitmap
        val dst = Bitmap.createBitmap(src, 0, 0, src.width, src.height, m, false)
        dst.density = DisplayMetrics.DENSITY_DEFAULT
        return BitmapDrawable(dst)
    }

    private fun cropBitmapCenter(bitmap: Bitmap, cropWidth: Int, cropHeight: Int): Bitmap {
        var cropWidth = cropWidth
        var cropHeight = cropHeight
        val bitmapWidth = bitmap.width
        val bitmapheight = bitmap.height

        // make sure crop isn't larger than bitmap size
        cropWidth = if (cropWidth > bitmapWidth) bitmapWidth else cropWidth
        cropHeight = if (cropHeight > bitmapheight) bitmapheight else cropHeight

        val newX = bitmapWidth / 2 - cropWidth / 2
        val newY = bitmapheight / 2 - cropHeight / 2

        return Bitmap.createBitmap(bitmap, newX, newY, cropWidth, cropHeight)
    }

}