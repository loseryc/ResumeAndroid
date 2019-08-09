package com.oo.resume.viewmodel

import androidx.annotation.MainThread
import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.oo.platform.repo.AbsentLiveData
import com.oo.platform.repo.RepositoryFactory
import com.oo.platform.viewmodel.BaseViewModel
import com.oo.platform.viewmodel.SingleLiveEvent
import com.oo.resume.data.response.ResumeDTO
import com.oo.resume.net.ResposeResult
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

    private val resumeDetail: LiveData<ResposeResult<ResumeDTO>>
    private val resumeDetailEvent = SingleLiveEvent<String>()

    init {

        resumeList = Transformations.switchMap(
                refreshEvent,
                Function<String, LiveData<ResposeResult<List<ResumeDTO>>>> { request ->
                    if (request == null) return@Function AbsentLiveData.create()
                    resumeRepo.getResumeList()
                })

        resumeDetail = Transformations.switchMap(
                resumeDetailEvent,
                Function { resumeDetailEvent ->
                    if (resumeDetailEvent.isNullOrBlank()) return@Function AbsentLiveData.create()
                    resumeRepo.getResumeDetail(resumeDetailEvent)
                })

    }


    fun refresh() {
        refreshEvent.value = ""
    }

    @MainThread
    fun resumeDetail(resumeId: String?) {
        resumeDetailEvent.value = resumeId
    }

    fun getResumeDetail():LiveData<ResposeResult<ResumeDTO>>{
        return resumeDetail
    }

    fun getResumeList(): LiveData<ResposeResult<List<ResumeDTO>>> {
        return resumeList
    }

}