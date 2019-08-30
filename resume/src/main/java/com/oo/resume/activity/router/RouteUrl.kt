package com.oo.resume.activity.router

/**
 *  $author yangchao
 *  $email  cd.uestc.superyoung@gmail.com
 *  $date   2019-06-24 10:20
 *  $describe
 */
interface RouteUrl {
    companion object {
        const val HOME_PAGE = "home_page"
        const val SIGNIN_PAGE = "signin_page"
        const val RESET_PASSWORD_PAGE = "reset_password_page"

        const val RESUME_DETAIL_PAGE = "resume_detail_page"
        const val EDIT_RESUME_BASE_INFO = "edit_resume_base_info"
    }
}