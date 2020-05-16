package com.money.game.ui.payment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.money.game.data.model.ApiResponse
import com.money.game.data.model.User.TransferBody
import com.money.game.data.model.User.User
import com.money.game.data.model.event.BuyEventInput
import com.money.game.data.model.event.BuyEventResult
import com.money.game.data.model.event.Event
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

    private val eventDetailResult = MutableLiveData<Event>()
    private val buyEventResult = MutableLiveData<BuyEventResult>()

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

    fun getEventDetailResult(): LiveData<Event>{
        return eventDetailResult
    }

    fun getBuyEventResult(): LiveData<BuyEventResult>{
        return buyEventResult
    }

    fun buyEvent(id: Int){
        val body: BuyEventInput = BuyEventInput()
        body.eventId = id
        loading.setValue(true)
        disposable?.add(webService.buyEvent("Bearer "+SharedPrefHelper.apiToken, body).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribeWith(object :
                DisposableSingleObserver<ApiResponse<BuyEventResult>>() {
                override fun onSuccess(response: ApiResponse<BuyEventResult>) {
                    loading.setValue(false)
                    if (response.success)
                        buyEventResult.postValue(response.data)
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




    fun getEventDetail(id:Int){
        loading.setValue(true)
        disposable?.add(webService.getEventDetail("Bearer "+SharedPrefHelper.apiToken, id).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribeWith(object :
                DisposableSingleObserver<ApiResponse<Event>>() {
                override fun onSuccess(response: ApiResponse<Event>) {
                    loading.setValue(false)
                    if (response.success)
                        eventDetailResult.postValue(response.data)
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