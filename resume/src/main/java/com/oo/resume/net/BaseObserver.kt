package com.oo.resume.net

import com.oo.resume.data.response.ErrorBody
import io.reactivex.annotations.Nullable
import io.reactivex.observers.DisposableObserver
import retrofit2.HttpException
import java.io.IOException

/**
 *  $author yangchao
 *  $email  cd.uestc.superyoung@gmail.com
 *  $date   2019-06-03 10:23
 *  $describe
 */
abstract class BaseObserver<T> : DisposableObserver<T>() {

    override fun onError(e: Throwable) {
        onApiError(asApiErrors(e))
    }

    protected open fun onApiError(@Nullable errors: ErrorBody?){

    }

    private fun asApiErrors(throwable: Throwable): ErrorBody? {
        // We had non-200 http error
        if (throwable is HttpException) {
            try {
                val response = throwable.response()
                val body = response?.errorBody()
                if (body == null) {
                    return null
                }
                val converter = RetrofitClient.get().responseBodyConverter<ErrorBody>(
                    ErrorBody::class.java,
                    arrayOfNulls(0)
                )
                return converter.convert(body)
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: RuntimeException) {
                e.printStackTrace()
            }

        }
        return null
    }

    override fun onComplete() {
    }

}