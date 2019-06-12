package com.oo.platform.view

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.oo.platform.util.StatusBarUtils

/**
 *  $author yangchao
 *  $email  cd.uestc.superyoung@gmail.com
 *  $date   2019-06-02 16:29
 *  $describe
 */
abstract class BaseActivity : AppCompatActivity() {

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindContentViewRes()
        if (isAllowRemoveBackgroundDrawable()) {
            window.setBackgroundDrawable(null)
        }
        if (isAllowTintStatusBar()) {
            StatusBarUtils.tintColor(this, tintStatusBar())
        }
    }

    open fun isAllowTintStatusBar(): Boolean {
        return true
    }

    open fun tintStatusBar(): Int {
        return ContextCompat.getColor(this, android.R.color.white)
    }

    /**s
     * default implementation
     */
    private fun bindContentViewRes() {
        if (getContentViewResId() != 0) {
            setContentView(getContentViewResId())
        }
    }

    private fun isAllowRemoveBackgroundDrawable(): Boolean {
        return true
    }

    @LayoutRes
    protected abstract fun getContentViewResId(): Int
}