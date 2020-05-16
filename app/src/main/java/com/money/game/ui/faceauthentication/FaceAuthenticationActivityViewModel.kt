package com.money.game.ui.faceauthentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.money.game.data.model.ApiResponse
import com.money.game.data.model.User.User
import com.money.game.data.rest.WebService
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


class FaceAuthenticationActivityViewModel @Inject
constructor(private val webService: WebService) : ViewModel() {


    private var disposable: CompositeDisposable? = null

    private val loading = MutableLiveData<Boolean>()
    private val errorMessage = MutableLiveData<String>()
    private val kickUser = MutableLiveData<String>()
    private val loginResult = MutableLiveData<User>()


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

    fun getLoginResult(): LiveData<User> {
        return loginResult
    }

    fun doLogin(
        phone: String, file: File)
    {
        loading.setValue(true)
        var map = HashMap<String, RequestBody>()
        map.put("phone_no", createPartFromString("65"+phone))
        val reqFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
        val photo = MultipartBody.Part.createFormData("photo", file.name, reqFile)



        disposable?.add(webService.verifyUser(
            photo,
            map
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribeWith(object :
                DisposableSingleObserver<ApiResponse<User>>() {
                override fun onSuccess(response: ApiResponse<User>) {
                    loading.setValue(false)
                    if (response.success)
                        loginResult.postValue(response.data)
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

    override fun onCleared() {
        super.onCleared()
        if (disposable != null) {
            disposable!!.clear()
            disposable = null
        }
    }


    private fun createPartFromString(descriptionString: String): RequestBody {
        return RequestBody.create(
            okhttp3.MultipartBody.FORM, descriptionString
        )
    }
}
