package com.oo.resume.net

import com.oo.resume.BuildConfig
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var sRetrofitInstance: Retrofit

    init {

        val client = OkHttpClient().newBuilder()
            .addInterceptor(SessionInterceptor())
            .addInterceptor(ResponseInterceptor())
            .build()

        sRetrofitInstance = Retrofit.Builder()
            .client(client)
            .baseUrl(BuildConfig.API_HOST)
            .addConverterFactory(ResponseResultFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallFactory.create(Schedulers.io(), AndroidSchedulers.mainThread())).build()
    }

    fun get(): Retrofit {
        return sRetrofitInstance
    }

    fun <T> getService(clazz: Class<T>): T {
        return get().create(clazz)
    }

}
