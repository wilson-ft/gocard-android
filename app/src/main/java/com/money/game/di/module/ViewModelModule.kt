package com.money.game.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.money.game.di.util.ViewModelFactory
import com.money.game.di.util.ViewModelKey
import com.money.game.ui.faceauthentication.FaceAuthenticationActivityViewModel
import com.money.game.ui.home.HomeActivityViewModel
import com.money.game.ui.payment.PaymentSummaryViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap


@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(FaceAuthenticationActivityViewModel::class)
    internal abstract fun bindFaceAuthenticationActivityViewModel(faceAuthenticationActivityViewModel: FaceAuthenticationActivityViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(HomeActivityViewModel::class)
    internal abstract fun bindHomeActivityViewModel(homeActivityViewModel: HomeActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PaymentSummaryViewModel::class)
    internal abstract fun bindPaymentSummaryActivityViewModel(paymentSummaryViewModel: PaymentSummaryViewModel): ViewModel


    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}