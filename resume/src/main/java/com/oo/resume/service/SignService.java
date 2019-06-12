package com.oo.resume.service;

import com.oo.resume.bean.Account;
import com.oo.resume.param.request.LoginRequest;
import com.oo.resume.param.request.RegistRequest;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface SignService {

    @POST("/account/regist")
    Observable<Account> regist(@Body RegistRequest params);

    @POST("/account/login")
    Observable<Account> login(@Body LoginRequest params);

    @PUT("/account/update")
    Observable<Account> update(@Body Account params);

}
