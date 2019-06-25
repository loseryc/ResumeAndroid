package com.oo.resume.repository

import android.content.Context
import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.oo.platform.repo.IRepository
import com.oo.resume.util.SpUtil

/**
 *  $author yangchao
 *  $email  cd.uestc.superyoung@gmail.com
 *  $date   2019-06-06 17:46
 *  $describe
 */
class SessionRepo : IRepository {

    private var sessionUser: MutableLiveData<String> = MutableLiveData()
    private var sessionKey: MutableLiveData<String> = MutableLiveData()
    private var loginStatue: MediatorLiveData<Boolean> = MediatorLiveData()

    init {
        transformations()
        restoreSession()
    }

    private fun restoreSession() {
        val sp = SpUtil.get(SESSION_SP_NAME, Context.MODE_PRIVATE)
        if (sp == null) return
        setSession(sp.getString(SP_KEY_SESSION_USER, null), sp.getString(SP_KEY_SESSION_KEY, null))
    }

    private fun storeSession(sessionUser: String?, sessionKey: String?) {
        val sp = SpUtil.get(SESSION_SP_NAME, Context.MODE_PRIVATE)
        if (sp == null) return
        val editor = sp.edit()
        editor.putString(SP_KEY_SESSION_USER, sessionUser)
        editor.putString(SP_KEY_SESSION_KEY, sessionKey)
        editor.commit()
    }

    private fun transformations() {
        loginStatue.addSource(sessionUser, Observer { _ ->
            loginStatue.value = isLogin()
        })
    }

    public fun isLogin(): Boolean {
        return !TextUtils.isEmpty(sessionKey.value) && !TextUtils.isEmpty(sessionUser.value)
    }

    companion object {
        const val SESSION_SP_NAME = "session"
        const val SP_KEY_SESSION_USER = "session-user"
        const val SP_KEY_SESSION_KEY = "session-key"
    }

    fun getLoginStatue(): LiveData<Boolean> {
        return loginStatue
    }

    fun getSessionUser(): LiveData<String> {
        return sessionUser
    }

    fun getSessionKey(): LiveData<String> {
        return sessionKey
    }

    fun setSession(sessionUser: String?, sessionKey: String?) {
        this.sessionKey.postValue(sessionKey)
        this.sessionUser.postValue(sessionUser)
        storeSession(sessionUser, sessionKey)
    }

}