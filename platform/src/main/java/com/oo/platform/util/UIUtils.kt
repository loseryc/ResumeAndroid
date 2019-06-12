package com.oo.platform.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import android.text.TextUtils
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import android.widget.TextView

import java.util.concurrent.atomic.AtomicInteger

/**
 * 界面 utils
 * Created by Lynn on 2017/4/20.
 */

object UIUtils {
    private val TRUNCATE_END: CharSequence = "..."

    private val sNextGeneratedId = AtomicInteger(1)

    private var sScreenWidth = 0
    private var sScreenHeight = 0
    private var sDensity = 0f

    /**
     * 转换 dp -> px
     */
    fun dp2px(context: Context, dp: Int): Int {
        return (context.resources.displayMetrics.density * dp + 0.5f).toInt()
    }

    /**
     * 转换 px -> dp
     */
    fun px2dp(context: Context, px: Int): Int {
        return (px / context.resources.displayMetrics.density + 0.5f).toInt()
    }

    /**
     * 获取屏幕宽度
     */
    fun getScreenWidth(context: Context?): Int {
        if (context == null) {
            return sScreenWidth
        }

        if (sScreenWidth == 0) {
            val displayMetrics = DisplayMetrics()
            val wm = getWindowManager(context)
            wm!!.defaultDisplay.getMetrics(displayMetrics)
            sScreenWidth = displayMetrics.widthPixels
        }
        return sScreenWidth
    }

    /**
     * 获取屏幕高度
     */
    fun getScreenHeight(context: Context?): Int {
        if (context == null) {
            return sScreenHeight
        }

        if (sScreenHeight == 0) {
            val displayMetrics = DisplayMetrics()
            val wm = getWindowManager(context)
            wm!!.defaultDisplay.getMetrics(displayMetrics)
            sScreenHeight = displayMetrics.heightPixels
        }
        return sScreenHeight
    }

    /**
     * 获取屏幕密度
     */
    fun getDensity(context: Context?): Float {
        if (context == null) {
            return sDensity
        }

        if (sDensity == 0f) {
            val displayMetrics = DisplayMetrics()
            val wm = getWindowManager(context)
            wm!!.defaultDisplay.getMetrics(displayMetrics)
            sDensity = displayMetrics.density
        }
        return sDensity
    }

    private fun getWindowManager(context: Context?): WindowManager? {
        return if (context == null) {
            null
        } else context
                .applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    }

    /**
     * 解决 maxline 与 ellipsize 的冲突
     * 截取超出内容，尾部替换省略号
     * 每次更新 text 的时候都需要调用
     * TODO 后续可以考虑兼容 ellipsize start / middle , 一般没有需求
     */
    @JvmOverloads
    fun applyEllipsizeEndCompat(textView: TextView?, filterBreak: Boolean = false) {
        if (textView == null) {
            return
        }

        textView.post(Runnable {
            if (filterBreak) {
                val str = textView.text.toString()
                val s = str.replace("\n", "")
                textView.text = s
            }
            val maxLines = textView.maxLines
            if (textView.lineCount > maxLines) {
                // 获取第 maxline 行 最后字符的 offset
                val line = textView.layout.getLineEnd(maxLines - 1)
                if (line <= TRUNCATE_END.length) {
                    // 极端情况，一行宽度只能容纳一个字符, 一般不需要考虑
                    return@Runnable
                }
                // CharSequence 是为了兼容 spannable 或者特殊字符, 如 emoji 等效果
                var text = textView.text
                try {
                    // 减去 省略号 占用 3 个字符长度
                    text = text.subSequence(0, line - TRUNCATE_END.length)
                    textView.text = text
                    textView.append(TRUNCATE_END)
                } catch (e: Exception) {
                    // 如果截取失败，不做替换
                }

            }
        })
    }

    @SuppressLint("NewApi")
    fun generateViewId(): Int {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            while (true) {
                val result = sNextGeneratedId.get()
                // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
                var newValue = result + 1
                if (newValue > 0x00FFFFFF) {
                    // Roll over to 1, not 0.
                    newValue = 1
                }
                if (sNextGeneratedId.compareAndSet(result, newValue)) {
                    return result
                }
            }
        } else {
            return View.generateViewId()
        }
    }

    @ColorInt
    @JvmOverloads
    fun parseColor(colorStr: String, @ColorInt defaultColor: Int = Color.WHITE): Int {
        if (TextUtils.isEmpty(colorStr)) {
            return defaultColor
        }

        try {
            return if (colorStr[0] == '#') {
                Color.parseColor(colorStr)
            } else {
                Color.parseColor(String.format("#%s", colorStr))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return defaultColor
        }

    }

    fun isColorDarker(@ColorInt color: Int): Boolean {
        val r = Color.red(color)
        val g = Color.green(color)
        val b = Color.blue(color)
        val darkness = (0.2126 * r + 0.7152 * g + 0.0722 * b) / 255
        return if (darkness > 0.5) {
            // It's a light color
            false
        } else {
            // It's a dark color
            true
        }
    }

    fun tintDrawableCompat(context: Context,
                           @DrawableRes drawableRes: Int,
                           @ColorRes colorStateList: Int): Drawable? {
        return tintDrawableCompat(ContextCompat.getDrawable(context, drawableRes)!!.mutate(),
                ContextCompat.getColorStateList(context, colorStateList))
    }

    fun tintDrawableCompat(context: Context,
                           @DrawableRes drawableRes: Int,
                           colorStateList: ColorStateList): Drawable? {
        return tintDrawableCompat(ContextCompat.getDrawable(context, drawableRes)!!.mutate(), colorStateList)
    }

    /**
     * drawable tint compat
     * 兼容着色效果
     */
    private fun tintDrawableCompat(drawable: Drawable?, colorStateList: ColorStateList?): Drawable? {
        if (drawable == null) {
            return null
        }
        try {
            val wrap = DrawableCompat.wrap(drawable.mutate().constantState!!.newDrawable())
            DrawableCompat.setTintList(wrap, colorStateList)
            return wrap
        } catch (e: NullPointerException) {
            return null
        }

    }
}
