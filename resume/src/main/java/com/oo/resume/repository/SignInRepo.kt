package com.oo.resume.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.oo.platform.repo.IRepository
import com.oo.resume.net.ResposeResult
import com.oo.resume.net.BaseObserver
import com.oo.resume.net.RetrofitClient
import com.oo.resume.param.request.LoginRequest
import com.oo.resume.param.request.RegistRequest
import com.oo.resume.param.response.AccountDTO
import com.oo.resume.param.response.ErrorBody
import com.oo.resume.service.SignService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 *  $author yangchao
 *  $email  cd.uestc.superyoung@gmail.com
 *  $date   2019-06-12 11:29
 *  $describe
 */
class SignInRepo : IRepository {


    fun login(request: LoginRequest?): LiveData<ResposeResult<AccountDTO>> {
        val observable = MutableLiveData<ResposeResult<AccountDTO>>()
        observable.setValue(ResposeResult.loading(null))
        RetrofitClient
            .get()
            .create(SignService::class.java)
            .login(request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : BaseObserver<AccountDTO>() {

                override fun onNext(it: AccountDTO) {
                    observable.postValue(ResposeResult.success(it))
                }


                override fun onApiError(errors: ErrorBody?) {
                    observable.postValue(ResposeResult.failure(errors))
                }

            })
        return observable
    }

    fun regist(request: RegistRequest?): LiveData<ResposeResult<AccountDTO>> {
        val observable = MutableLiveData<ResposeResult<AccountDTO>>()
        observable.setValue(ResposeResult.loading(null))
        RetrofitClient
            .get()
            .create(SignService::class.java)
            .regist(request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : BaseObserver<AccountDTO>() {

                override fun onNext(it: AccountDTO) {
                    observable.postValue(ResposeResult.success(it))
                }


                override fun onApiError(errors: ErrorBody?) {
                    observable.postValue(ResposeResult.failure(errors))
                }

            })
        return observable
    }
}