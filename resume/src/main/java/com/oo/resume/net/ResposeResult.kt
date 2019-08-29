package com.oo.resume.net

import com.oo.platform.repo.StatusValue
import com.oo.resume.data.response.ErrorBody

/**
 *  $author yangchao
 *  $email  cd.uestc.superyoung@gmail.com
 *  $date   2019-06-12 11:56
 *  $describe
 */
class ResposeResult<Data>(status: Int, responseBody: Data?, errors: ErrorBody?) :
    ResposeResultWithErrorType<Data, ErrorBody>(status, responseBody, errors) {
    companion object {

        fun <Data> success(responseBody: Data?): ResposeResult<Data> {
            return ResposeResult(StatusValue.SUCCESS, responseBody, null)
        }

        fun <Data> loading(): ResposeResult<Data> {
            return loading(null)
        }

        fun <Data> loading(responseBody: Data?): ResposeResult<Data> {
            return ResposeResult(StatusValue.LOADING, responseBody, null)
        }

        fun <Data> failure(errors: ErrorBody?): ResposeResult<Data> {
            return failure(null, errors)
        }

        fun <Data> failure(responseBody: Data?, errors: ErrorBody?): ResposeResult<Data> {
            return ResposeResult(StatusValue.FAILURE, responseBody, errors)
        }
    }
}