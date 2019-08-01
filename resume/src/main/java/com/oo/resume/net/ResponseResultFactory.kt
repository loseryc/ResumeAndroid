package com.oo.resume.net

/**
 *  $author yangchao
 *  $email  cd.uestc.superyoung@gmail.com
 *  $date   2019-08-01 17:13
 *  $describe
 */

import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Bypass the default retrofit body converter behaviour
 * as we are using the [Resource] as an response wrapper we should
 * tell to the original converter the correct type
 */
class ResponseResultFactory private constructor() : Converter.Factory() {

    override fun responseBodyConverter(
        type: Type?, annotations: Array<Annotation>?,
        retrofit: Retrofit?
    ): Converter<ResponseBody, *>? {
        if (type is ParameterizedType) {
            var parameterizedType: ParameterizedType = type
            if (parameterizedType.rawType === Response::class.java) {
                val subType = parameterizedType.actualTypeArguments[0]
                if (subType is ParameterizedType) {
                    parameterizedType = parameterizedType.actualTypeArguments[0] as ParameterizedType
                }
            }

            if (parameterizedType.rawType === ResposeResult::class.java) {
                val realType = parameterizedType.actualTypeArguments[0]
                return retrofit!!.nextResponseBodyConverter<Any>(this, realType, annotations!!)
            }
        }
        return retrofit!!.nextResponseBodyConverter<Any>(this, type!!, annotations!!)
    }

    companion object {

        fun create(): ResponseResultFactory {
            return ResponseResultFactory()
        }


        @Deprecated("use {@link #create()} instead")
        fun wrap(factory: Converter.Factory): ResponseResultFactory {
            return create()
        }
    }
}