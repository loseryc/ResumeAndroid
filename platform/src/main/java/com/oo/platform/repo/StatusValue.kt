package com.oo.platform.repo

import androidx.annotation.IntDef
import com.oo.platform.repo.StatusValue.Companion.FAILURE
import com.oo.platform.repo.StatusValue.Companion.LOADING
import com.oo.platform.repo.StatusValue.Companion.SUCCESS
import com.oo.platform.repo.StatusValue.Companion.WARNING


@IntDef(value = [SUCCESS, FAILURE, LOADING, WARNING])
@Retention(AnnotationRetention.SOURCE)
annotation class StatusValue {
    companion object {
        const val SUCCESS = 0
        const val FAILURE = 1
        const val LOADING = 2
        const val WARNING = 3
    }
}
