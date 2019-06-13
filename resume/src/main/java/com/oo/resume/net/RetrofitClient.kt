package com.oo.resume.net

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.protobuf.ProtoConverterFactory

/**
 * Created by Lynn on 2018/10/12.
 */
object RetrofitClient {
    private var sRetrofitInstance: Retrofit? = null

    @Synchronized
    fun get(): Retrofit {
        if (sRetrofitInstance == null) {
            sRetrofitInstance = createRetrofitInstance().build()
        }
        return sRetrofitInstance!!
    }


    private fun createRetrofitInstance(): Retrofit.Builder {
        val client = OkHttpClient().newBuilder()
            .addInterceptor(SessionInterceptor())
            .build()
        return Retrofit.Builder()
            .client(client)
            .baseUrl("http://172.25.168.203")
            .addConverterFactory(ProtoConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    }

}
