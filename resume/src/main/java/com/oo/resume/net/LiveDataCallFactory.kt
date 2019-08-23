package com.oo.resume.net

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.oo.resume.data.const.ApiErrorCode
import com.oo.resume.data.response.ErrorBody
import io.reactivex.Scheduler
import retrofit2.*
import java.io.IOException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.net.ConnectException
import java.net.SocketTimeoutException


class LiveDataCallFactory private constructor(
    val subscribWorker: Scheduler.Worker,
    val observeWorker: Scheduler.Worker
) : CallAdapter.Factory() {

    companion object {

        fun create(subscribScheduler: Scheduler, observeScheduler: Scheduler): LiveDataCallFactory {
            return LiveDataCallFactory(
                subscribScheduler.createWorker(),
                observeScheduler.createWorker()
            )
        }
    }

    public override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != LiveData::class.java) {
            return null
        }

        if (returnType !is ParameterizedType) {
            throw IllegalStateException("Response must be parametrized as " + "LiveData<ResposeResult> or LiveData<? extends ResposeResult>")
        }

        val responseType = getParameterUpperBound(0, returnType)
        return when (getRawType(responseType)) {
            ResposeResult::class.java -> {
                if (responseType !is ParameterizedType) {
                    throw IllegalStateException("Response must be parametrized as " + "LiveData<Response<ResposeResult>> or LiveData<Response<? extends ResposeResult>>")
                }

                LiveDataResponseCallAdapter<Any>(responseType)
            }
            else -> LiveDataCallAdapter<Any>(responseType)
        }
    }

    inner class LiveDataCallAdapter<ResponseBody> internal constructor(private val responseType: Type) :
        CallAdapter<ResponseBody, LiveData<ResponseBody>> {

        override fun responseType(): Type {
            return responseType
        }

        override fun adapt(call: Call<ResponseBody>): LiveData<ResponseBody> {
            val liveDataResponse = MutableLiveData<ResponseBody>()
            subscribWorker.schedule { call.enqueue(LiveDataCallCallback(liveDataResponse)) }
            return liveDataResponse
        }

        private inner class LiveDataCallCallback internal constructor(private val liveData: MutableLiveData<ResponseBody>) :
            Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (call.isCanceled) return
                observeWorker.schedule {
                    if (response.isSuccessful) {
                        liveData.postValue(response.body())
                    } else {
                        onFailure(call, HttpException(response))
                    }
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                if (call.isCanceled) return
                observeWorker.schedule {
                    liveData.postValue(null)
                }
            }
        }
    }

    inner class LiveDataResponseCallAdapter<ResponseBody> internal constructor(private val responseType: Type) :
        CallAdapter<ResponseBody, LiveData<ResposeResult<ResponseBody>>> {

        override fun responseType(): Type {
            return responseType
        }

        override fun adapt(call: Call<ResponseBody>): LiveData<ResposeResult<ResponseBody>> {
            val liveDataResponse = MutableLiveData<ResposeResult<ResponseBody>>()
            liveDataResponse.postValue(ResposeResult.loading())
            subscribWorker.schedule { call.enqueue(LiveDataResponseCallCallback(liveDataResponse)) }
            return liveDataResponse
        }

        private inner class LiveDataResponseCallCallback internal constructor(private val liveData: MutableLiveData<ResposeResult<ResponseBody>>) :
            Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (call.isCanceled) return
                observeWorker.schedule {
                    if (response.isSuccessful) {
                        liveData.postValue(ResposeResult.success(response.body()))
                    } else {
                        onFailure(call, HttpException(response))
                    }
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                if (call.isCanceled) return
                observeWorker.schedule {
                    liveData.postValue(ResposeResult.failure(asApiErrors(t)))
                }
            }
        }

        private fun asApiErrors(throwable: Throwable): ErrorBody? {
            // We had non-200 http error
            return when (throwable) {
                is HttpException -> handApiError(throwable)
                is SocketTimeoutException -> ErrorBody(
                    ApiErrorCode.SOCKET_TIMEOUT,
                    throwable.message
                )
                is ConnectException -> ErrorBody(
                    ApiErrorCode.NETWORK_UNREACHABLE,
                    throwable.message
                )
                else -> ErrorBody(ApiErrorCode.UNKNOW, throwable.message)
            }
        }

        private fun handApiError(throwable: HttpException): ErrorBody? {
            val response = throwable.response()
            val body = response?.errorBody()
                ?: return ErrorBody(ApiErrorCode.UNKNOW, throwable.message)
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
    }


}
