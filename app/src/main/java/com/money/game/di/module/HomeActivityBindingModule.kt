package com.money.game.di.module

import com.money.game.ui.home.HomeFragment
import com.money.game.ui.map.MapFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
public abstract class HomeActivityBindingModule {

    @ContributesAndroidInjector
    internal abstract fun provideHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    internal abstract fun provideMapFragment(): MapFragment

}