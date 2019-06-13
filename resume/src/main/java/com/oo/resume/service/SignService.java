package com.oo.resume.service;

import com.oo.resume.bean.Account;
import com.oo.resume.param.path.UrlConst;
import com.oo.resume.param.request.LoginRequest;
import com.oo.resume.param.request.RegistRequest;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface SignService {

    @POST(UrlConst.ACCOUNT_PREFIX + UrlConst.ACCOUNT_REGIST)
    Observable<Account> regist(@Body RegistRequest params);

    @POST(UrlConst.ACCOUNT_PREFIX + UrlConst.ACCOUNT_LOGIN)
    Observable<Account> login(@Body LoginRequest params);

    @PUT(UrlConst.ACCOUNT_PREFIX + UrlConst.ACCOUNT_UPDATE)
    Observable<Account> update(@Body Account params);

}
