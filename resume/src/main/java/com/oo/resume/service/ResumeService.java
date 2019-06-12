package com.oo.resume.service;

import com.oo.resume.entity.Resume;
import io.reactivex.Observable;
import retrofit2.http.GET;

import java.util.List;

public interface ResumeService {

    @GET("/resume/list")
    Observable<List<Resume>> getResumeList();
}
