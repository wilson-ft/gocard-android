package com.money.game.di.module

import com.money.game.ui.faceauthentication.FaceAuthenticationActivity
import com.money.game.ui.home.HomeActivity
import com.money.game.ui.payment.PaymentSummaryActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
public abstract class ActivityBindingModule {

    @ContributesAndroidInjector
    abstract fun bindFaceAuthenticationActivity(): FaceAuthenticationActivity

    @ContributesAndroidInjector(modules = [HomeActivityBindingModule::class])
    internal abstract fun bindHomeActivity(): HomeActivity

    @ContributesAndroidInjector
    abstract fun bindPaymentSummaryActivity(): PaymentSummaryActivity

}