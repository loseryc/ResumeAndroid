package com.oo.resume.param.path

/**
 *   yangchao
 *    cd.uestc.superyoung@gmail.com
 *     2019-06-08 11:23
 *
 */
interface UrlConst {
    companion object {
        const val RESUME_PARAMS_RESUME_ID = "resumeId"
        const val RESUME_PREFIX = "/resume"
        const val RESUME_LIST = "/list"
        const val RESUME_DELETE = "/delete/{$RESUME_PARAMS_RESUME_ID}"

        const val REVIEW_PARAM_SHORT_LINK = "shortLink"
        const val REVIEW_PREFIX = "/review"
        const val REVIEW_RESUME = "/{$REVIEW_PARAM_SHORT_LINK}"

        const val ACCOUNT_PREFIX = "/account"
        const val ACCOUNT_REGIST = "/regist"
        const val ACCOUNT_LOGIN = "/login"
        const val ACCOUNT_UPDATE = "/update"
    }
}