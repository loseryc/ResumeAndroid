package com.oo.resume.service

import com.oo.resume.net.RetrofitClient
import com.oo.resume.param.path.UrlConst
import com.oo.resume.param.response.ResumeDTO
import io.reactivex.Observable
import retrofit2.http.GET

interface ResumeService {

    @GET(UrlConst.RESUME_PREFIX + UrlConst.RESUME_LIST)
    fun getResumeList(): Observable<List<ResumeDTO>>

}
