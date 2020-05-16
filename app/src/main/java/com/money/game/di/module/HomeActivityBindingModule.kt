package com.money.game.di.module

import com.money.game.ui.home.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
public abstract class HomeActivityBindingModule {

    @ContributesAndroidInjector
    internal abstract fun provideHomeFragment(): HomeFragment

}