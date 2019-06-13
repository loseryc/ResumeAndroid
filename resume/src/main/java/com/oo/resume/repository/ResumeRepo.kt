package com.oo.resume.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.oo.platform.repo.IRepository
import com.oo.resume.net.ResposeResult
import com.oo.resume.net.BaseObserver
import com.oo.resume.net.RetrofitClient
import com.oo.resume.param.response.ErrorBody
import com.oo.resume.param.response.ResumeDTO
import com.oo.resume.service.ResumeService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

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
            .get()
            .create(ResumeService::class.java)
            .getResumeList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : BaseObserver<List<ResumeDTO>>() {

                override fun onNext(it: List<ResumeDTO>) {
                    observable.postValue(ResposeResult.success(it))
                }


                override fun onApiError(errors: ErrorBody?) {
                    observable.postValue(ResposeResult.failure(errors))
                }

            })
        return observable
    }

}