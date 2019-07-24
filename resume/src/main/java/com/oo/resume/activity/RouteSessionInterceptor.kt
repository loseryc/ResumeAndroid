package com.oo.resume.activity

import android.content.Intent
import android.net.Uri
import com.chenenyu.router.RouteInterceptor
import com.chenenyu.router.RouteResponse
import com.oo.platform.repo.RepositoryFactory
import com.oo.resume.repository.SessionRepo

/**
 *  $author yangchao
 *  $email  cd.uestc.superyoung@gmail.com
 *  $date   2019-06-24 10:31
 *  $describe
 */
class RouteSessionInterceptor : RouteInterceptor {

    private val sessionRepo = RepositoryFactory.getRepository(SessionRepo::class.java)

    override fun intercept(chain: RouteInterceptor.Chain): RouteResponse {
        if (sessionRepo.isLogin()) return chain.process()
        chain.request.uri = Uri.parse(RouteUrl.SIGNIN_PAGE)
        chain.request.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        return chain.process()
    }

}