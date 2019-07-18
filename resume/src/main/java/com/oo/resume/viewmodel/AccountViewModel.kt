package com.oo.resume.viewmodel

import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.oo.platform.repo.AbsentLiveData
import com.oo.platform.repo.RepositoryFactory
import com.oo.platform.viewmodel.BaseViewModel
import com.oo.platform.viewmodel.SingleLiveEvent
import com.oo.resume.net.ResposeResult
import com.oo.resume.data.response.AccountDTO
import com.oo.resume.repository.AccountRepo

/**
 *  $author yangchao
 *  $email  cd.uestc.superyoung@gmail.com
 *  $date   2019-06-12 13:23
 *  $describe
 */
class AccountViewModel : BaseViewModel() {

    private val accountRepo = RepositoryFactory.getRepository(AccountRepo::class.java)
    private val updateEvent = SingleLiveEvent<AccountDTO>()
    private val accountResult: LiveData<ResposeResult<AccountDTO>>

    init {
        accountResult = Transformations.switchMap(
            updateEvent,
            Function<AccountDTO, LiveData<ResposeResult<AccountDTO>>> { request ->
                if (request == null) return@Function AbsentLiveData.create()
                accountRepo.update(request)
            })

    }

    fun update(account: AccountDTO?) {
        updateEvent.value = account
    }

    fun getAccountInfo(): LiveData<AccountDTO> {
        return accountRepo.getAccountInfo()
    }

    fun getUpdateResult(): LiveData<ResposeResult<AccountDTO>> {
        return accountResult
    }

}