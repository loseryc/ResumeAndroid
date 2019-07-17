package com.oo.resume.net

import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.lang.reflect.Type

class RxJava2AsyncAutoMainCallAdapterFactory private constructor() : CallAdapter.Factory() {

    private var mFactory: RxJava2CallAdapterFactory = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())

    companion object {
        fun create(): RxJava2AsyncAutoMainCallAdapterFactory {
            return RxJava2AsyncAutoMainCallAdapterFactory()
        }
    }


    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *>? {
        val callAdapter = mFactory.get(returnType, annotations, retrofit)

        if (null == callAdapter) {
            return null
        }

        when (getRawType(returnType)) {
            Flowable::class.java -> return FlowableCallAdapter(callAdapter as CallAdapter<Flowable<*>, Flowable<*>>)
            Observable::class.java -> return ObservableCallAdapter(callAdapter as CallAdapter<Observable<*>, Observable<*>>)
        }

        return callAdapter
    }

    private inner class ObservableCallAdapter internal constructor(private val mAdapter: CallAdapter<Observable<*>, Observable<*>>) :
        CallAdapter<Observable<*>, Observable<*>> {
        override fun responseType(): Type {
            return mAdapter.responseType()
        }

        override fun adapt(call: Call<Observable<*>>): Observable<*> {
            return mAdapter.adapt(call).observeOn(AndroidSchedulers.mainThread())
        }
    }

    private inner class FlowableCallAdapter internal constructor(private val mAdapter: CallAdapter<Flowable<*>, Flowable<*>>) :
        CallAdapter<Flowable<*>, Flowable<*>> {
        override fun responseType(): Type {
            return mAdapter.responseType()
        }

        override fun adapt(call: Call<Flowable<*>>): Flowable<*> {
            return mAdapter.adapt(call).observeOn(AndroidSchedulers.mainThread())
        }
    }

}
