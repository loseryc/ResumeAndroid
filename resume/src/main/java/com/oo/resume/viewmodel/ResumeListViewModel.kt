package com.oo.resume.viewmodel

import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.oo.platform.repo.AbsentLiveData
import com.oo.platform.repo.RepositoryFactory
import com.oo.platform.viewmodel.BaseViewModel
import com.oo.platform.viewmodel.SingleLiveEvent
import com.oo.resume.net.ResposeResult
import com.oo.resume.param.response.ResumeDTO
import com.oo.resume.repository.ResumeRepo

/**
 *  $author yangchao
 *  $email  cd.uestc.superyoung@gmail.com
 *  $date   2019-06-12 13:23
 *  $describe
 */
class ResumeListViewModel : BaseViewModel() {

    private val resumeRepo = RepositoryFactory.getRepository(ResumeRepo::class.java)
    private val refreshEvent = SingleLiveEvent<String>()

    private val resumeList: LiveData<ResposeResult<List<ResumeDTO>>>

    init {

        resumeList = Transformations.switchMap(
            refreshEvent,
            Function<String, LiveData<ResposeResult<List<ResumeDTO>>>> { request ->
                if (request == null) return@Function AbsentLiveData.create()
                resumeRepo.getResumeList()
            })

    }


    fun refresh() {
        refreshEvent.value = ""
    }

    fun getResumeList():LiveData<ResposeResult<List<ResumeDTO>>>{
        return resumeList
    }

}