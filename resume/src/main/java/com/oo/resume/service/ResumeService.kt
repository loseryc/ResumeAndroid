package com.oo.resume.service

import androidx.lifecycle.LiveData
import com.oo.resume.data.path.ResumeUrl
import com.oo.resume.data.response.ResumeDTO
import com.oo.resume.net.ResposeResult
import retrofit2.http.GET

interface ResumeService {

    @GET(ResumeUrl.PREFIX + ResumeUrl.PATH_LIST)
    fun getResumeList(): LiveData<ResposeResult<List<ResumeDTO>>>

}
