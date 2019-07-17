package com.oo.resume.net

import android.text.TextUtils
import com.oo.platform.repo.RepositoryFactory
import com.oo.resume.param.header.HeaderConst
import com.oo.resume.repository.SessionRepo
import okhttp3.Interceptor
import okhttp3.Response

/**
 *  $author yangchao
 *  $email  cd.uestc.superyoung@gmail.com
 *  $date   2019-06-12 14:23
 *  $describe
 */
class SessionInterceptor : Interceptor {

    private val sessionRepo = RepositoryFactory.getRepository(SessionRepo::class.java)

    override fun intercept(chain: Interceptor.Chain): Response {
        val sessionKey = sessionRepo.getSessionKey().value
        val sessionUser = sessionRepo.getSessionUser().value
        val original = chain.request()
        if (TextUtils.isEmpty(sessionKey) || TextUtils.isEmpty(sessionUser)) return chain.proceed(original)

        val request = original.newBuilder().method(original.method, original.body).headers(original.headers)
        try {
            request.addHeader(HeaderConst.SESSION_USER, sessionUser!!)
            request.addHeader(HeaderConst.SESSION_KEY, sessionKey!!)
        } catch (e: RuntimeException) {
            e.printStackTrace()
        }

        return chain.proceed(request.build())
    }

}