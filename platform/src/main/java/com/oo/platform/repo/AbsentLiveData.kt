package com.oo.platform.repo

import androidx.lifecycle.LiveData


class AbsentLiveData : LiveData<Any>() {
    init {
        postValue(null)
    }

    companion object {

        fun <T> create(): LiveData<T> {
            return AbsentLiveData() as LiveData<T>
        }
    }
}
