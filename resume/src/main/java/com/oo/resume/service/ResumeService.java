package com.oo.resume.service;

import com.oo.resume.param.path.UrlConst;
import com.oo.resume.param.response.ResumeDTO;
import io.reactivex.Observable;
import retrofit2.http.GET;

import java.util.List;

public interface ResumeService {

    @GET(UrlConst.RESUME_PREFIX + UrlConst.RESUME_LIST)
    Observable<List<ResumeDTO>> getResumeList();
}
