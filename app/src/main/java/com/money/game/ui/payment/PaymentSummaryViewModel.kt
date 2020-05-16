package com.money.game.ui.payment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.money.game.data.model.ApiResponse
import com.money.game.data.model.User.TransferBody
import com.money.game.data.model.User.User
import com.money.game.data.rest.WebService
import com.money.game.utils.SharedPrefHelper
import com.money.game.utils.Utility
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.HashMap
import javax.inject.Inject

class PaymentSummaryViewModel @Inject
constructor(private val webService: WebService) : ViewModel() {

    private var disposable: CompositeDisposable? = null

    private val loading = MutableLiveData<Boolean>()
    private val errorMessage = MutableLiveData<String>()
    private val kickUser = MutableLiveData<String>()

    private val transferResult = MutableLiveData<User>()

    init {
        disposable = CompositeDisposable()
    }

    fun getErrorMessage(): LiveData<String> {
        return errorMessage
    }

    fun getAuthErrorMessage(): LiveData<String> {
        return kickUser
    }

    fun getLoading(): LiveData<Boolean> {
        return loading
    }

    fun getTransferResult(): LiveData<User>{
        return transferResult
    }

    fun deductBalance(amount: Double){
        val body: TransferBody = TransferBody()
        body.type = "transfer"
        body.amount = amount
        loading.setValue(true)
        disposable?.add(webService.modifyBalance("Bearer "+SharedPrefHelper.apiToken, body).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribeWith(object :
                DisposableSingleObserver<ApiResponse<User>>() {
                override fun onSuccess(response: ApiResponse<User>) {
                    loading.setValue(false)
                    if (response.success)
                        transferResult.postValue(response.data)
                    else {
                        errorMessage.postValue(response.message)
                    }
                }

                override fun onError(e: Throwable) {

                    Utility.handleAPIError(e, errorMessage, kickUser)
                    loading.setValue(false)
                }
            })
        )
    }
}