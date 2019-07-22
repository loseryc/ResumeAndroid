package com.oo.resume.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.oo.platform.repo.IRepository
import com.oo.resume.net.BaseObserver
import com.oo.resume.net.ResposeResult
import com.oo.resume.net.RetrofitClient
import com.oo.resume.data.response.ErrorBody
import com.oo.resume.data.response.ResumeDTO
import com.oo.resume.service.ResumeService

/**
 *  $author yangchao
 *  $email  cd.uestc.superyoung@gmail.com
 *  $date   2019-06-12 11:29
 *  $describe
 */
class ResumeRepo : IRepository {

    fun getResumeList(): LiveData<ResposeResult<List<ResumeDTO>>> {
        val observable = MutableLiveData<ResposeResult<List<ResumeDTO>>>()
        observable.setValue(ResposeResult.loading(null))
        RetrofitClient
                .getService(ResumeService::class.java)
                .getResumeList()

                .subscribe(object : BaseObserver<List<ResumeDTO>>() {

                    override fun onNext(it: List<ResumeDTO>) {
                        observable.postValue(ResposeResult.success(it))
                    }


                    override fun onError(errors: ErrorBody?) {
                        observable.postValue(ResposeResult.failure(errors))
                    }

                })
        return observable
    }

}