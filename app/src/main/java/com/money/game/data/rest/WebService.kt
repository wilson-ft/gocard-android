package com.money.game.data.rest

import com.money.game.data.model.ApiResponse
import com.money.game.data.model.User.LoginInput
import com.money.game.data.model.User.TransferBody
import com.money.game.data.model.User.User
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*


public interface WebService {

    @POST("api/login")
    abstract fun doLogin(@Body loginInput: LoginInput): Single<ApiResponse<User>>

    @Multipart
    @POST("api/users/verify")
    abstract fun verifyUser(
        @Part photo: MultipartBody.Part,
        @PartMap params: HashMap<String, RequestBody>
    ): Single<ApiResponse<User>>

    @GET("api/users")
    abstract fun getUserDetail(@Header("Authorization") token: String): Single<ApiResponse<User>>

    @POST("api/users/transfer")
    abstract fun modifyBalance(@Header("Authorization") token: String, @Body body: TransferBody): Single<ApiResponse<User>>


}