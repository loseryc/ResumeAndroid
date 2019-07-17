package com.oo.resume.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.oo.platform.repo.IRepository
import com.oo.resume.net.BaseObserver
import com.oo.resume.net.ResposeResult
import com.oo.resume.net.RetrofitClient
import com.oo.resume.param.request.LoginRequest
import com.oo.resume.param.request.RegistRequest
import com.oo.resume.param.response.AccountDTO
import com.oo.resume.param.response.ErrorBody
import com.oo.resume.service.AccountService
import com.oo.resume.util.SpUtil

/**
 *  $author yangchao
 *  $email  cd.uestc.superyoung@gmail.com
 *  $date   2019-06-12 11:29
 *  $describe
 */
class AccountRepo : IRepository {

    private var account: MediatorLiveData<AccountDTO> = MediatorLiveData()


    init {
        restoreAccount()
    }

    fun getAccountInfo(): LiveData<AccountDTO> {
        return account
    }

    private fun storeAccount(account: AccountDTO?) {
        val sp = SpUtil.get(ACCOUNT_SP_NAME, Context.MODE_PRIVATE)
        if (sp == null || account == null) return
        this.account.postValue(account)
        val editor = sp.edit()
        editor.putString(KEY_ACCOUNT_INFO, Gson().toJson(account))
        editor.commit()
    }

    private fun restoreAccount() {
        val sp = SpUtil.get(ACCOUNT_SP_NAME, Context.MODE_PRIVATE) ?: return
        val accountJson = sp.getString(KEY_ACCOUNT_INFO, "")
        if (accountJson.isNullOrEmpty()) return
        this.account.postValue(Gson().fromJson(accountJson, AccountDTO::class.java))
    }

    fun login(request: LoginRequest): LiveData<ResposeResult<AccountDTO>> {
        val observable = MutableLiveData<ResposeResult<AccountDTO>>()
        observable.setValue(ResposeResult.loading(null))
        RetrofitClient
                .getService(AccountService::class.java)
                .login(request)
                .subscribe(object : BaseObserver<AccountDTO>() {

                    override fun onNext(it: AccountDTO) {
                        storeAccount(it)
                        observable.postValue(ResposeResult.success(it))
                    }

                    override fun onApiError(errors: ErrorBody?) {
                        observable.postValue(ResposeResult.failure(errors))
                    }

                })
        return observable
    }

    fun update(request: AccountDTO): LiveData<ResposeResult<AccountDTO>> {
        val observable = MutableLiveData<ResposeResult<AccountDTO>>()
        observable.value = ResposeResult.loading(null)
        RetrofitClient
                .getService(AccountService::class.java)
                .update(request)
                .subscribe(object : BaseObserver<AccountDTO>() {

                    override fun onNext(it: AccountDTO) {
                        storeAccount(it)
                        observable.postValue(ResposeResult.success(it))
                    }

                    override fun onApiError(errors: ErrorBody?) {
                        observable.postValue(ResposeResult.failure(errors))
                    }

                })
        return observable
    }

    fun regist(request: RegistRequest): LiveData<ResposeResult<AccountDTO>> {
        val observable = MutableLiveData<ResposeResult<AccountDTO>>()
        observable.setValue(ResposeResult.loading(null))
        RetrofitClient
                .getService(AccountService::class.java)
                .regist(request)
                .subscribe(object : BaseObserver<AccountDTO>() {

                    override fun onNext(it: AccountDTO) {
                        storeAccount(it)
                        observable.postValue(ResposeResult.success(it))
                    }

                    override fun onApiError(errors: ErrorBody?) {
                        observable.postValue(ResposeResult.failure(errors))
                    }

                })
        return observable
    }

    companion object {
        const val ACCOUNT_SP_NAME = "account"
        const val KEY_ACCOUNT_INFO = "account_info"
    }
}