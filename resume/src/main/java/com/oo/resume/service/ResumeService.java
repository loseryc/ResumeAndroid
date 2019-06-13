package com.oo.resume.service;

import com.oo.resume.entity.Resume;
import com.oo.resume.param.path.UrlConst;
import io.reactivex.Observable;
import retrofit2.http.GET;

import java.util.List;

public interface ResumeService {

    @GET(UrlConst.RESUME_PREFIX + UrlConst.RESUME_LIST)
    Observable<List<Resume>> getResumeList();
}
