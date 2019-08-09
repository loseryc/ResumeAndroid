package com.oo.resume.net

import com.chenenyu.router.Router
import com.oo.platform.repo.RepositoryFactory
import com.oo.resume.ResumeApplication
import com.oo.resume.activity.router.RouteUrl
import com.oo.resume.repository.SessionRepo
import okhttp3.Interceptor
import okhttp3.Response

/**
 *  $author yangchao
 *  $email  cd.uestc.superyoung@gmail.com
 *  $date   2019-06-12 14:23
 *  $describe
 */
class ResponseInterceptor : Interceptor {

    private val sessionRepo = RepositoryFactory.getRepository(SessionRepo::class.java)

    override fun intercept(chain: Interceptor.Chain): Response {
        val result = chain.proceed(chain.request())
        if (result.code == 401) {//未登录
            sessionRepo.setSession(null, null)
            Router.build(RouteUrl.SIGNIN_PAGE).go(ResumeApplication.INSTANCE)
        }
        return result
    }

}