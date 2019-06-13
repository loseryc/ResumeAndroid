package com.oo.resume.service;

import com.oo.resume.param.path.UrlConst;
import com.oo.resume.param.request.LoginRequest;
import com.oo.resume.param.request.RegistRequest;
import com.oo.resume.param.response.AccountDTO;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface SignService {

    @POST(UrlConst.ACCOUNT_PREFIX + UrlConst.ACCOUNT_REGIST)
    Observable<AccountDTO> regist(@Body RegistRequest params);

    @POST(UrlConst.ACCOUNT_PREFIX + UrlConst.ACCOUNT_LOGIN)
    Observable<AccountDTO> login(@Body LoginRequest params);

    @PUT(UrlConst.ACCOUNT_PREFIX + UrlConst.ACCOUNT_UPDATE)
    Observable<AccountDTO> update(@Body AccountDTO params);

}
