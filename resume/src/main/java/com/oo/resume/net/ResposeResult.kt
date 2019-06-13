package com.oo.resume.net

import com.oo.platform.repo.BaseResult
import com.oo.platform.repo.StatusValue
import com.oo.resume.param.response.ErrorBody

/**
 *  $author yangchao
 *  $email  cd.uestc.superyoung@gmail.com
 *  $date   2019-06-12 11:56
 *  $describe
 */
class ResposeResult<T>(status: Int, data: T?, errors: ErrorBody?) : BaseResult<T, ErrorBody>(status, data, errors) {

    companion object {

        fun <T> success(data: T): ResposeResult<T> {
            return ResposeResult(StatusValue.SUCCESS, data, null)
        }

        fun <T> loading(): ResposeResult<T> {
            return loading(null)
        }

        fun <T> loading(data: T?): ResposeResult<T> {
            return ResposeResult(StatusValue.LOADING, data, null)
        }

        fun <T> failure(errors: ErrorBody?): ResposeResult<T> {
            return failure(null, errors)
        }

        fun <T> failure(data: T?, errors: ErrorBody?): ResposeResult<T> {
            return ResposeResult(StatusValue.FAILURE, data, errors)
        }
    }
}