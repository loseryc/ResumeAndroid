package com.oo.platform.repo

import androidx.lifecycle.LiveData


class AbsentLiveData<T> : LiveData<T>() {
    init {
        postValue(null)
    }

    companion object {

        fun <T> create(): LiveData<T> {
            return AbsentLiveData()
        }
    }
}
