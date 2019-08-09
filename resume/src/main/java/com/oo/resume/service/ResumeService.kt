package com.oo.resume.service

import androidx.lifecycle.LiveData
import com.oo.resume.data.path.ResumeUrl
import com.oo.resume.data.response.ResumeDTO
import com.oo.resume.net.ResposeResult
import retrofit2.http.GET
import retrofit2.http.Path

interface ResumeService {

    @GET(ResumeUrl.PREFIX + ResumeUrl.PATH_LIST)
    fun getResumeList(): LiveData<ResposeResult<List<ResumeDTO>>>


    @GET(ResumeUrl.PREFIX + ResumeUrl.PATH_DETAIL)
    fun getResumeDetail(@Path(ResumeUrl.PARAMS_RESUME_ID) resumeId: String): LiveData<ResposeResult<ResumeDTO>>

}
