package com.oo.resume.repository

import androidx.lifecycle.LiveData
import com.oo.platform.repo.IRepository
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

    fun getResumeList(): LiveData<ResposeResult<List<ResumeDTO>>> {
        return RetrofitClient
            .getService(ResumeService::class.java)
            .getResumeList()
    }

}