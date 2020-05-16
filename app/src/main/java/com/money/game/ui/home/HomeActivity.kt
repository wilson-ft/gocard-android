package com.money.game.ui.home

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import com.money.game.R
import com.money.game.base.BaseActivity
import com.money.game.databinding.ActivityHomeBinding
import com.money.game.di.util.ViewModelFactory
import com.money.game.ui.faceauthentication.FaceAuthenticationActivityViewModel
import com.money.game.ui.map.MapFragment
import javax.inject.Inject

class HomeActivity:BaseActivity(), OnClickListener{

    @Inject
    lateinit  var viewModelFactory: ViewModelFactory
    internal var viewModel: HomeActivityViewModel?=null

    var homeFragment: HomeFragment = HomeFragment()
    var mapFragment: MapFragment = MapFragment()

    var binding: ActivityHomeBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(HomeActivityViewModel::class.java)


        showHomeFragment()

    }

    override fun onResume() {
        super.onResume()

        viewModel?.getUserDetail()
    }

    override fun onClick(p0: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun showHomeFragment() {

        if(homeFragment.isVisible)
            return

        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fl_content, homeFragment, "HOME")
        ft.commit()
    }

    fun showMapFragment() {
        if(mapFragment.isVisible)
            return
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fl_content, mapFragment, "HOME")
        ft.addToBackStack(null)
        ft.commit()
    }

}