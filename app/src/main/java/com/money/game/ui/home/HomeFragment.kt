package com.money.game.ui.home

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.money.game.R
import com.money.game.base.BaseFragment
import com.money.game.data.model.User.User
import com.money.game.databinding.FragmentHomeBinding
import com.money.game.di.util.ViewModelFactory
import com.money.game.utils.SharedPrefHelper
import com.money.game.utils.Utility
import javax.inject.Inject

class HomeFragment: BaseFragment(){


    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    internal var viewModel: HomeActivityViewModel? =null

    var binding : FragmentHomeBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel =
            ViewModelProviders.of(activity!!, viewModelFactory).get(HomeActivityViewModel::class.java)
        viewModel?.getErrorMessage()?.observe(this, Observer { this.showSnackBar(it) })
        viewModel?.getAuthErrorMessage()?.observe(this, Observer { kickUser(it) })
        viewModel?.getLoading()?.observe(this, Observer { this.showLoading(it) })
        viewModel?.getUserDetailResult()?.observe(this, Observer { onUserDetailReturn(it) })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_home, container, false);
        binding = DataBindingUtil.bind(v)
        updateData()
        binding?.llDining?.setOnClickListener({
            var homeActivity:HomeActivity? = activity as? HomeActivity

            homeActivity?.showMapFragment()
        })
        binding?.ivLogout?.setOnClickListener({
            showLogoutConfirmation()
        })
        return v;
    }

    fun showLogoutConfirmation(){
        val dialog: Dialog
        dialog = Dialog(this.context!!)
//        dialog.window!!.setBackgroundDrawable(
//            ContextCompat.getDrawable(
//                this.context!!,
//                R.drawable.bg_dialog
//            )
//        )
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_confirmation)
        val tvTitle = dialog.findViewById(R.id.tv_title) as TextView
        val tvDesc = dialog.findViewById(R.id.tv_desc) as TextView
        val tvCancel = dialog.findViewById(R.id.tv_cancel) as TextView
        val tvConfirm = dialog.findViewById(R.id.tv_confirm) as TextView

        tvTitle.setText("Are you sure?")
        tvDesc.setText("You are logging out from your account")
        tvConfirm.setOnClickListener({kickUser("You have been successfully logged out")})
        tvCancel.setOnClickListener({dialog.dismiss()})

        dialog.show()

    }

    override fun onResume() {
        super.onResume()
    }

    fun onUserDetailReturn(user: User){
        SharedPrefHelper.updateUser(user)
        updateData()
    }

    fun updateData(){
        binding?.tvTitle?.setText("Hi, "+SharedPrefHelper.firstName)
        binding?.tvFullName?.setText(SharedPrefHelper.firstName+" "+ SharedPrefHelper.lastName)
        binding?.tvBalance?.setText("S$"+Utility.currencyFormat(""+SharedPrefHelper.balance))
    }

}