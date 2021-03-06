package com.money.game.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.money.game.data.model.ApiResponse
import com.money.game.data.model.User.User
import com.money.game.data.model.event.Category
import com.money.game.data.model.event.Event
import com.money.game.data.rest.WebService
import com.money.game.utils.SharedPrefHelper
import com.money.game.utils.Utility
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeActivityViewModel @Inject
constructor(private val webService: WebService) : ViewModel() {

    private var disposable: CompositeDisposable? = null

    private val loading = MutableLiveData<Boolean>()
    private val errorMessage = MutableLiveData<String>()
    private val kickUser = MutableLiveData<String>()

    private val userDetailResult = MutableLiveData<User>()

    private val eventDetailResult = MutableLiveData<Event>()

    private val userCategoryListResult = MutableLiveData<User>()

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

    fun getUserDetailResult(): LiveData<User>{
        return userDetailResult
    }


    fun getEventDetailResult(): LiveData<Event>{
        return eventDetailResult
    }

    fun getUserCategoryListResult(): LiveData<User>{
        return userCategoryListResult
    }

    fun getUserDetail(){
        loading.setValue(true)
        disposable?.add(webService.getUserDetail("Bearer "+SharedPrefHelper.apiToken).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribeWith(object :
                DisposableSingleObserver<ApiResponse<User>>() {
                override fun onSuccess(response: ApiResponse<User>) {
                    loading.setValue(false)
                    if (response.success)
                        userDetailResult.postValue(response.data)
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


    fun getCategoryList(){
        disposable?.add(webService.getCategories("Bearer "+SharedPrefHelper.apiToken).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribeWith(object :
                DisposableSingleObserver<ApiResponse<User>>() {
                override fun onSuccess(response: ApiResponse<User>) {
                    if (response.success)
                        userCategoryListResult.postValue(response.data)
                    else {
                        errorMessage.postValue(response.message)
                    }
                }

                override fun onError(e: Throwable) {

                    Utility.handleAPIError(e, errorMessage, kickUser)
                }
            })
        )
    }
}