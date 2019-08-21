package com.oo.resume.net

import com.oo.platform.repo.BaseResult
import com.oo.platform.repo.StatusValue
import com.oo.resume.data.response.ErrorBody

/**
 *  $author yangchao
 *  $email  cd.uestc.superyoung@gmail.com
 *  $date   2019-06-12 11:56
 *  $describe
 */
class ResposeResult<T>(status: Int, responseBody: T?, errors: ErrorBody?) : BaseResult<T, ErrorBody>(status, responseBody, errors) {

    companion object {

        fun <T> success(responseBody: T?): ResposeResult<T> {
            return ResposeResult(StatusValue.SUCCESS, responseBody, null)
        }

        fun <T> loading(): ResposeResult<T> {
            return loading(null)
        }

        fun <T> loading(responseBody: T?): ResposeResult<T> {
            return ResposeResult(StatusValue.LOADING, responseBody, null)
        }

        fun <T> failure(errors: ErrorBody?): ResposeResult<T> {
            return failure(null, errors)
        }

        fun <T> failure(responseBody: T?, errors: ErrorBody?): ResposeResult<T> {
            return ResposeResult(StatusValue.FAILURE, responseBody, errors)
        }
    }
}