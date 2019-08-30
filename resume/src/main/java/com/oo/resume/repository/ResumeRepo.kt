package com.oo.resume.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.oo.platform.repo.IRepository
import com.oo.resume.data.response.BaseInfoDTO
import com.oo.resume.data.response.ResumeDTO
import com.oo.resume.net.ResposeResult
import com.oo.resume.net.RetrofitClient
import com.oo.resume.service.ResumeService

/**
 *  $author yangchao
 *  $email  cd.uestc.superyoung@gmail.com
 *  $date   2019-06-12 11:29
 *  $describe
 */
class ResumeRepo : IRepository {

    private val baseInfo = MutableLiveData<BaseInfoDTO>()

    fun getResumeList(): LiveData<ResposeResult<List<ResumeDTO>>> {
        return RetrofitClient
                .getService(ResumeService::class.java)
                .getResumeList()
    }

    fun getResumeDetail(resumeId: Long): LiveData<ResposeResult<ResumeDTO>> {
        return RetrofitClient
                .getService(ResumeService::class.java)
                .getResumeDetail(resumeId)
    }

    fun getBaseInfo() = baseInfo

}