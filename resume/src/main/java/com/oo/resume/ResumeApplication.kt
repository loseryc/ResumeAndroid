package com.oo.resume

import android.app.Application
import androidx.multidex.MultiDexApplication
import com.chenenyu.router.Router
import com.facebook.drawee.backends.pipeline.Fresco
import com.oo.resume.activity.RouteSessionInterceptor

/**
 *  $author yangchao
 *  $email  cd.uestc.superyoung@gmail.com
 *  $date   2019-06-02 17:18
 *  $describe
 */
class ResumeApplication : MultiDexApplication() {

    companion object {
        var INSTANCE: Application? = null
    }

    init {
        INSTANCE = this
    }

    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
        Router.addGlobalInterceptor(RouteSessionInterceptor())
    }


}