package com.oo.resume.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import com.oo.platform.repo.RepositoryFactory
import com.oo.platform.view.BaseActivity
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
        object : CountDownTimer(TimeUnit.SECONDS.toMillis(3), TimeUnit.SECONDS.toMillis(1)) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                val clazz = if (sessionRepo.isLogin()) ResumeListActivity::class.java else SignInActivity::class.java
                startActivity(Intent(this@SplashActivity, clazz))
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


    override fun onDestroy() {
        timer?.cancel()
        timer = null
        super.onDestroy()
    }

    override fun getContentViewResId(): Int {
        return 0
    }

}