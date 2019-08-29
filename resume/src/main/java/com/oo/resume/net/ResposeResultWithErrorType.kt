package com.oo.resume.net

import com.oo.platform.repo.BaseResult
import com.oo.platform.repo.StatusValue

/**
 *  $author yangchao
 *  $email  cd.uestc.superyoung@gmail.com
 *  $date   2019-06-12 11:56
 *  $describe
 */
open class ResposeResultWithErrorType<Data, Error>(
    status: Int,
    responseBody: Data?,
    errors: Error?
) :
    BaseResult<Data, Error>(status, responseBody, errors) {

    companion object {

        fun <Data, Error> success(responseBody: Data?): ResposeResultWithErrorType<Data, Error> {
            return ResposeResultWithErrorType(StatusValue.SUCCESS, responseBody, null)
        }

        fun <Data, Error> loading(): ResposeResultWithErrorType<Data, Error> {
            return loading(null)
        }

        fun <Data, Error> loading(responseBody: Data?): ResposeResultWithErrorType<Data, Error> {
            return ResposeResultWithErrorType(StatusValue.LOADING, responseBody, null)
        }

        fun <Data, Error> failure(errors: Error?): ResposeResultWithErrorType<Data, Error> {
            return failure(null, errors)
        }

        fun <Data, Error> failure(
            responseBody: Data?,
            errors: Error?
        ): ResposeResultWithErrorType<Data, Error> {
            return ResposeResultWithErrorType(StatusValue.FAILURE, responseBody, errors)
        }
    }
}