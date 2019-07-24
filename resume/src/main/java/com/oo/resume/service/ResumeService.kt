package com.oo.resume.service

import com.oo.resume.data.path.ResumeUrl
import com.oo.resume.data.response.ResumeDTO
import io.reactivex.Observable
import retrofit2.http.GET

interface ResumeService {

    @GET(ResumeUrl.PREFIX + ResumeUrl.PATH_LIST)
    fun getResumeList(): Observable<List<ResumeDTO>>

}
