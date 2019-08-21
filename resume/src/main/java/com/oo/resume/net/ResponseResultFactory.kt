package com.oo.resume.net

/**
 *  $author yangchao
 *  $email  cd.uestc.superyoung@gmail.com
 *  $date   2019-08-01 17:13
 *  $describe
 */

import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Bypass the default retrofit body converter behaviour
 * as we are using the [ResposeResult] as an response wrapper we should
 * tell to the original converter the correct type
 */
class ResponseResultFactory private constructor() : Converter.Factory() {

    override fun responseBodyConverter(
        type: Type, annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *> {
        if (type is ParameterizedType && type.rawType === ResposeResult::class.java) {
            return retrofit.nextResponseBodyConverter<Any>(this, type.actualTypeArguments[0], annotations)
        }
        return retrofit.nextResponseBodyConverter<Any>(this, type, annotations)
    }

    companion object {

        fun create(): ResponseResultFactory {
            return ResponseResultFactory()
        }
    }
}