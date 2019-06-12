package com.oo.resume.util

import android.content.SharedPreferences
import com.oo.resume.ResumeApplication

/**
 *  $author yangchao
 *  $email  cd.uestc.superyoung@gmail.com
 *  $date   2019-06-12 10:46
 *  $describe
 */
class SpUtil {
    companion object {
        fun get(name: String, mode: Int): SharedPreferences? {
            return ResumeApplication.INSTANCE?.getSharedPreferences(name, mode)
        }
    }
}