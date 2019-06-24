package com.oo.resume.activity

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.chenenyu.router.Router
import com.chenenyu.router.annotation.Route
import com.oo.platform.view.BaseActivity
import com.oo.platform.viewmodel.ViewModelBinder
import com.oo.resume.R
import com.oo.resume.constance.Regex
import com.oo.resume.param.request.LoginRequest
import com.oo.resume.param.request.RegistRequest
import com.oo.resume.viewmodel.SignInViewModel
import kotlinx.android.synthetic.main.activity_sign_in.*
import java.util.regex.Pattern

@Route(RouteUrl.SIGNIN_PAGE)
class SignInActivity : BaseActivity() {

    private lateinit var loading: AlertDialog

    private lateinit var viewmodel: SignInViewModel

    override fun getContentViewResId(): Int {
        return R.layout.activity_sign_in
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindViewModel()
        observe()
        setListener()
        initLoadingDialog()
    }

    private fun initLoadingDialog() {
        loading = AlertDialog.Builder(this)
            .setMessage("Loading")
            .setCancelable(false)
            .setIcon(R.mipmap.ic_launcher_round)
            .create()
    }

    private fun refreshUI(type: SignInViewModel.SignType) {
        when (type) {
            SignInViewModel.SignType.Regist -> {
                tv_sign_in_type.text = "密码登录"
                btn_confirm.text = "注册"
                tv_name_title.visibility = View.VISIBLE
                et_name.visibility = View.VISIBLE
            }
            else -> {
                tv_sign_in_type.text = "账号注册"
                btn_confirm.text = "登录"
                tv_name_title.visibility = View.INVISIBLE
                et_name.visibility = View.INVISIBLE
            }
        }
    }

    private fun bindViewModel() {
        viewmodel = ViewModelBinder.bind(this, SignInViewModel::class.java)
    }

    private fun observe() {
        viewmodel.getViewType().observe(this, Observer { refreshUI(it) })
        viewmodel.getLoginResult().observe(this, Observer { result ->
            if (result == null) return@Observer
            if (result.isLoading) {
                loading.show()
                return@Observer
            }
            loading.dismiss()
            if (result.isSuccess) {
                showToast("登录成功")
                Router.build(RouteUrl.HOME_PAGE).go(this)
                finish()
            } else if (result.errors != null) {
                showToast(result.errors.msg)
            }
        })

        viewmodel.getRegistResult().observe(this, Observer { result ->
            if (result == null) return@Observer
            if (result.isLoading) {
                loading.show()
                return@Observer
            }
            loading.dismiss()
            if (result.isSuccess) {
                showToast("注册成功")
                Router.build(RouteUrl.SIGNIN_PAGE).go(this)
                finish()
            } else if (result.errors != null) {
                showToast(result.errors.msg)
            }
        })
    }

    private fun setListener() {
        tv_sign_in_type.setOnClickListener { viewmodel.changeViewType() }
        btn_confirm.setOnClickListener { doConfirm() }
    }

    private fun doConfirm() {
        val name = et_name.text.toString()
        val phone = et_phone.text.toString()
        val password = et_password.text.toString()
        if (phone.isNullOrEmpty()) {
            showToast("电话不能为空")
            return
        }
        if (!Pattern.matches(Regex.MOBILE, phone)) {
            showToast("电话不合法")
            return
        }
        if (password.isNullOrEmpty()) {
            showToast("密码不能为空")
            return
        }
        when (viewmodel.getViewType().value) {
            SignInViewModel.SignType.Regist -> {
                if (name.isNullOrEmpty()) {
                    showToast("名字不能为空")
                    return
                }
                viewmodel.regist(RegistRequest(phone, password, name))
            }
            else -> viewmodel.login(LoginRequest(phone, password))
        }
    }


    private fun showToast(content: String?) {
        if (content == null) return
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show()
    }


}
