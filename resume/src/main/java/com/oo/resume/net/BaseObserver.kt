package com.oo.resume.net

import com.oo.resume.data.const.ApiErrorCode
import com.oo.resume.data.response.ErrorBody
import io.reactivex.annotations.Nullable
import io.reactivex.observers.DisposableObserver
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException

/**
 *  $author yangchao
 *  $email  cd.uestc.superyoung@gmail.com
 *  $date   2019-06-03 10:23
 *  $describe
 */
abstract class BaseObserver<T> : DisposableObserver<T>() {

    override fun onError(e: Throwable) {
        onError(asApiErrors(e))
    }

    private fun asApiErrors(throwable: Throwable): ErrorBody? {
        // We had non-200 http error
        when (throwable) {
            is HttpException -> return handApiError(throwable)
            is SocketTimeoutException -> return ErrorBody(ApiErrorCode.SOCKET_TIMEOUT, throwable.message)
            is ConnectException -> return ErrorBody(ApiErrorCode.NETWORK_UNREACHABLE, throwable.message)
            else -> return ErrorBody(ApiErrorCode.UNKNOW, throwable.message)
        }
    }

    private fun handApiError(throwable: HttpException): ErrorBody? {
        val response = throwable.response()
        val body = response?.errorBody()
        if (body == null) return ErrorBody(ApiErrorCode.UNKNOW, throwable.message)
        try {
            return RetrofitClient.get().responseBodyConverter<ErrorBody>(
                ErrorBody::class.java, arrayOfNulls(0)
            ).convert(body)
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: RuntimeException) {
            e.printStackTrace()
        }
        return ErrorBody(ApiErrorCode.UNKNOW, throwable.message)
    }

    protected open fun onError(@Nullable errors: ErrorBody?) {

    }

    override fun onComplete() {
    }

}