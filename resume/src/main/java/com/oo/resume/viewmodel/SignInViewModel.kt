package com.oo.resume.viewmodel

import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import com.oo.platform.repo.AbsentLiveData
import com.oo.platform.repo.RepositoryFactory
import com.oo.platform.viewmodel.BaseViewModel
import com.oo.resume.net.ResposeResult
import com.oo.resume.param.request.LoginRequest
import com.oo.resume.param.request.RegistRequest
import com.oo.resume.param.response.AccountDTO
import com.oo.resume.repository.SessionRepo
import com.oo.resume.repository.SignInRepo

/**
 *  $author yangchao
 *  $email  cd.uestc.superyoung@gmail.com
 *  $date   2019-06-12 13:23
 *  $describe
 */
class SignInViewModel : BaseViewModel() {

    private val signInRepo = RepositoryFactory.getRepository(SignInRepo::class.java)
    private val sessionRepo = RepositoryFactory.getRepository(SessionRepo::class.java)

    private val loginRequest = MutableLiveData<LoginRequest>()
    private val registRequest = MutableLiveData<RegistRequest>()

    private val viewType = MutableLiveData<SignType>()
    private val registResult: LiveData<ResposeResult<AccountDTO>>
    private val loginResult: LiveData<ResposeResult<AccountDTO>>

    private val loginResultObserver: Observer<ResposeResult<AccountDTO>>

    private val registResultObserver: Observer<ResposeResult<AccountDTO>>

    init {
        viewType.value = SignType.Login

        loginResult = Transformations.switchMap(
            loginRequest,
            Function<LoginRequest, LiveData<ResposeResult<AccountDTO>>> { request ->
                if (request == null) return@Function AbsentLiveData.create()
                signInRepo.login(request)
            })

        registResult = Transformations.switchMap(
            registRequest,
            Function<RegistRequest, LiveData<ResposeResult<AccountDTO>>> { request ->
                if (request == null) return@Function AbsentLiveData.create()
                signInRepo.regist(request)
            })

        loginResultObserver = Observer { result ->
            setSession(result)
        }

        registResultObserver = Observer { result ->
            setSession(result)
        }
        loginResult.observeForever(loginResultObserver)
        registResult.observeForever(registResultObserver)


    }

    private fun setSession(result: ResposeResult<AccountDTO>?) {
        if (result == null || !result.isSuccess || result.data == null
            || result.data.session_key.isNullOrEmpty()
            || result.data.session_user.isNullOrEmpty()
        ) return
        sessionRepo.setSession(result.data.session_user, result.data.session_key)
    }


    enum class SignType {
        Regist,
        Login
    }

    fun getViewType(): LiveData<SignType> {
        return viewType
    }

    fun getLoginResult(): LiveData<ResposeResult<AccountDTO>> {
        return loginResult
    }

    fun getRegistResult(): LiveData<ResposeResult<AccountDTO>> {
        return registResult
    }

    fun changeViewType() {
        when (viewType.value) {
            SignType.Regist -> viewType.value = SignType.Login
            else -> viewType.value = SignType.Regist
        }
    }

    fun login(request: LoginRequest?) {
        if (request == null) return
        loginRequest.value = request
    }

    fun regist(request: RegistRequest?) {
        if (request == null) return
        registRequest.value = request
    }
}