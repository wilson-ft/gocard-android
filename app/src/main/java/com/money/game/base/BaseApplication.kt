package com.money.game.base

import com.money.game.di.component.DaggerApplicationComponent
import com.money.game.utils.SharedPrefHelper
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class BaseApplication : DaggerApplication() {
    override fun onCreate() {
        super.onCreate()
        SharedPrefHelper.setContext(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val component = DaggerApplicationComponent.builder().application(this).build()
        component.inject(this)

        return component
    }
}
