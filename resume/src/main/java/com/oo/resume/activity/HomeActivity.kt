package com.oo.resume.activity

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.chenenyu.router.annotation.Route
import com.oo.platform.view.BaseActivity
import com.oo.resume.R
import com.oo.resume.activity.router.RouteUrl
import kotlinx.android.synthetic.main.activity_home.*
import kotlin.system.exitProcess

/**
 *  $author yangchao
 *  $email  cd.uestc.superyoung@gmail.com
 *  $date   2019-06-13 17:21
 *  $describe
 */
@Route(RouteUrl.HOME_PAGE)
class HomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        pager.adapter = HomeFragmentPageAdapter(supportFragmentManager)
        tabs.setupWithViewPager(pager)
        Page.values().forEachIndexed { index, page -> tabs.getTabAt(index)?.text = page.title }

    }

    inner class HomeFragmentPageAdapter(manager: FragmentManager) :
            FragmentPagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getItem(position: Int): Fragment {
            return when (Page.getPage(position)) {
                Page.ResumeList -> ResumeListFragment()
                Page.Mine -> MineFragment()
                else -> throw IllegalArgumentException("Did not handle this page enum!!!")
            }
        }

        override fun getCount(): Int {
            return Page.values().size
        }

    }

    private var mExitPressed: Boolean = false
    override fun onBackPressed() {
        if (mExitPressed) {
            exitProcess(0)
        } else {
            mExitPressed = true
            Toast.makeText(this, "再按一次退出应用", Toast.LENGTH_SHORT).show()

            object : CountDownTimer(2000, 1000) {
                override fun onTick(millisUntilFinished: Long) {

                }

                override fun onFinish() {
                    mExitPressed = false
                }
            }.start()
        }
    }

    enum class Page(val index: Int, val title: String) {
        ResumeList(0, "简历"),
        Mine(1, "我的");

        companion object {
            fun getPage(index: Int): Page? {
                return values().find { it.index == index }
            }
        }
    }

    override fun getContentViewResId(): Int {
        return R.layout.activity_home
    }

}