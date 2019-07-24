package com.oo.resume.service

import com.oo.resume.data.path.AccountUrl
import com.oo.resume.data.request.LoginRequest
import com.oo.resume.data.request.RegistRequest
import com.oo.resume.data.request.ResetPasswordRequest
import com.oo.resume.data.response.AccountDTO
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface AccountService {

    @POST(AccountUrl.PREFIX + AccountUrl.PATH_REGIST)
    fun regist(@Body params: RegistRequest): Observable<AccountDTO>

    @POST(AccountUrl.PREFIX + AccountUrl.PATH_LOGIN)
    fun login(@Body params: LoginRequest): Observable<AccountDTO>

    @PUT(AccountUrl.PREFIX + AccountUrl.PATH_UPDATE)
    fun update(@Body params: AccountDTO): Observable<AccountDTO>

    @PUT(AccountUrl.PREFIX + AccountUrl.PATH_RESET_PASSWORD)
    fun resetPassword(@Body params: ResetPasswordRequest): Observable<Boolean>

}
