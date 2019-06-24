package com.oo.resume.activity

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import com.chenenyu.router.Router
import com.oo.platform.repo.RepositoryFactory
import com.oo.platform.view.BaseActivity
import com.oo.resume.R
import com.oo.resume.repository.SessionRepo
import java.util.concurrent.TimeUnit

/**
 *  $author yangchao
 *  $email  cd.uestc.superyoung@gmail.com
 *  $date   2019-06-12 16:40
 *  $describe
 */
class SplashActivity : BaseActivity() {

    val sessionRepo = RepositoryFactory.getRepository(SessionRepo::class.java)
    private var timer: CountDownTimer? =
        object : CountDownTimer(TimeUnit.SECONDS.toMillis(2), TimeUnit.SECONDS.toMillis(1)) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                Router.build(
                    if (sessionRepo.isLogin()) RouteUrl.HOME_PAGE
                    else RouteUrl.SIGNIN_PAGE
                ).go(this@SplashActivity)
                finish()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        timer?.start()
    }

    override fun tintStatusBar(): Int {
        return Color.rgb(207, 107, 71)
    }

    override fun isAllowRemoveBackgroundDrawable(): Boolean {
        return false
    }


    override fun onDestroy() {
        timer?.cancel()
        timer = null
        super.onDestroy()
    }

    override fun getContentViewResId(): Int {
        return R.layout.activity_splash
    }

}