package com.oo.resume.repository

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

abstract class BaseChainRepo(lifecycleOwner: LifecycleOwner):IChainRepo,LifecycleObserver{

    private val mLifecycleOwner: LifecycleOwner = lifecycleOwner


    companion object{
        fun bindLife(){

        }
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun onDestroy(){

    }

    override fun add(lifecycleOwner: LifecycleOwner) {

    }
}
