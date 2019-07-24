package com.oo.resume.viewmodel

import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.oo.platform.repo.AbsentLiveData
import com.oo.platform.repo.RepositoryFactory
import com.oo.platform.viewmodel.BaseViewModel
import com.oo.platform.viewmodel.SingleLiveEvent
import com.oo.resume.data.request.ResetPasswordRequest
import com.oo.resume.data.response.AccountDTO
import com.oo.resume.net.ResposeResult
import com.oo.resume.repository.AccountRepo

/**
 *  $author yangchao
 *  $email  cd.uestc.superyoung@gmail.com
 *  $date   2019-06-12 13:23
 *  $describe
 */
class ResetPasswordViewModel : BaseViewModel() {

    private val accountRepo = RepositoryFactory.getRepository(AccountRepo::class.java)
    private val resetPasswordEvent = SingleLiveEvent<ResetPasswordRequest>()
    private val resetPasswordResult: LiveData<ResposeResult<Boolean>>

    init {
        resetPasswordResult = Transformations.switchMap(
            resetPasswordEvent,
            Function<ResetPasswordRequest, LiveData<ResposeResult<Boolean>>> { request ->
                if (request == null) return@Function AbsentLiveData.create()
                accountRepo.resetPassword(request)
            })

    }


    fun update(oldPassword: String, newPassword: String) {
        resetPasswordEvent.value = ResetPasswordRequest(oldPassword, newPassword)
    }


    fun getResetPasswordResult(): LiveData<ResposeResult<Boolean>> {
        return resetPasswordResult
    }

    fun getAccountInfo(): LiveData<AccountDTO> {
        return accountRepo.getAccountInfo()
    }

}