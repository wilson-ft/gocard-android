package com.money.game.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.money.game.R
import com.money.game.databinding.DialogLoadingBinding

class LoadingDialog: DialogFragment(){

    companion object {
        fun newInstance():LoadingDialog{
            return LoadingDialog()
        }
    }

    var binding: DialogLoadingBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(DialogFragment.STYLE_NORMAL,
            android.R.style.Theme_Black_NoTitleBar_Fullscreen);
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.dialog_loading, container, false)
        binding = DataBindingUtil.bind<DialogLoadingBinding>(v)
        dialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        return v
    }
}