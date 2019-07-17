package com.oo.resume.net

import android.util.ArrayMap
import com.oo.resume.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
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
            .addInterceptor(ResponseInterceptor())
            .build()
        return Retrofit.Builder()
            .client(client)
            .baseUrl(BuildConfig.API_HOST)
            .addConverterFactory(ProtoConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2AsyncAutoMainCallAdapterFactory.create())
    }

    private val mServicePools = ArrayMap<Class<*>, Any>()

    @Suppress("UNCHECKED_CAST")
    fun <T> getService(clazz: Class<T>): T {
        var repository: T? = null
        synchronized(RetrofitClient::class.java) {
            if (mServicePools.containsKey(clazz)) {
                repository = mServicePools[clazz] as T?
            }
            if (repository == null) {
                repository = createService(clazz)
                putService(clazz, repository)
            }
        }
        return repository as T
    }

    private fun <T> putService(clazz: Class<T>?): RetrofitClient {
        return putService(clazz, null)
    }

    private fun <T> putService(clazz: Class<T>?, repository: T?): RetrofitClient {
        if (clazz == null) {
            throw NullPointerException("repository class can not be null: ")
        }
        mServicePools[clazz] = repository
        return this
    }

    private fun <T> createService(clazz: Class<T>): T {
        return RetrofitClient.get().create(clazz)
    }

}
