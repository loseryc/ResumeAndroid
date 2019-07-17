package com.oo.platform.viewmodel

import android.content.Context
import android.content.ContextWrapper
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

/**
 *  $author yangchao
 *  $email  cd.uestc.superyoung@gmail.com
 *  $date   2019-06-11 18:03
 *  $describe
 */
object ViewModelBinder {

    fun <T : ViewModel> bind(activity: FragmentActivity, clazz: Class<T>): T {
        return bind(activity, clazz, null)
    }

    fun <T : ViewModel> bind(activity: FragmentActivity, clazz: Class<T>, factory: ViewModelProvider.Factory?): T {
        return ViewModelProviders.of(activity, factory).get(clazz)
    }

    fun <T : ViewModel> bind(fragment: Fragment, clazz: Class<T>): T {
        return bind(fragment, clazz, null)
    }

    fun <T : ViewModel> bind(fragment: Fragment, clazz: Class<T>, factory: ViewModelProvider.Factory?): T {
        return ViewModelProviders.of(fragment, factory).get(clazz)
    }

    fun <T : ViewModel> bind(contextWrapper: ContextWrapper, clazz: Class<T>): T {
        return bind(contextWrapper.baseContext, clazz, null)
    }

    fun <T : ViewModel> bind(contextWrapper: ContextWrapper, clazz: Class<T>, factory: ViewModelProvider.Factory?): T {
        return bind(contextWrapper.baseContext, clazz, factory)
    }

    fun <T : ViewModel> bind(context: Context, clazz: Class<T>): T {
        return ViewModelBinder.bind(context, clazz, null)
    }


    fun <T : ViewModel> bind(context: Context, clazz: Class<T>, factory: ViewModelProvider.Factory?): T {
        if (context is FragmentActivity) {
            return ViewModelProviders.of(context, factory).get(clazz)
        } else {
            throw Exception("ViewModelProviders only support fragment & fragmentActivity")
        }
    }

}