package com.money.game.di.module

import com.money.game.data.rest.WebService
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class ApplicationModule() {

    val BASE_URL = "https://floral-grove-mbve5x1g3hax.vapor-farm-b1.com/"

    @Singleton
    @Provides
    internal fun provideRetrofit(): Retrofit {

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response {
                val original = chain.request()

                val request = original.newBuilder()
//                    .header("User-Agent", "Your-App-Name")
                    .header("Accept", "application/json")
                    .method(original.method, original.body)
                    .build()

                return chain.proceed(request)
            }
        })
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)


        httpClient.addInterceptor(logging)
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
    }

    @Singleton
    @Provides
    internal fun provideWebService(retrofit: Retrofit): WebService {
        return retrofit.create<WebService>(WebService::class.java)
    }
}