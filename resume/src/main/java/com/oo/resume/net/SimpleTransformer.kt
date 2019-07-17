package com.oo.resume.net

import io.reactivex.FlowableTransformer
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SimpleTransformer private constructor() {


    companion object {

        fun <T> observableIo2Main(): ObservableTransformer<T, T> {
            return ObservableTransformer { upstream -> upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()) }
        }

        fun <T> flowableIo2Main(): FlowableTransformer<T, T> {
            return FlowableTransformer { upstream -> upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()) }
        }
    }


}
