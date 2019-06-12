package com.oo.platform.repo

import android.util.ArrayMap

/**
 *  $author yangchao
 *  $email  cd.uestc.superyoung@gmail.com
 *  $date   2019-06-11 17:22
 *  $describe
 */
object RepositoryFactory {

    private val mRepoPools = ArrayMap<Class<out IRepository>, IRepository>()

    @Suppress("UNCHECKED_CAST")
    fun <T : IRepository> getRepository(clazz: Class<T>): T {
        var repository: T? = null
        synchronized(RepositoryFactory::class.java) {
            if (mRepoPools.containsKey(clazz)) {
                repository = mRepoPools[clazz] as T?
            }
            if (repository == null) {
                repository = createRepository(clazz)
                putRepository(clazz, repository)
            }
        }
        return repository as T
    }

    private fun <T : IRepository> putRepository(clazz: Class<T>?): RepositoryFactory {
        return putRepository(clazz, null)
    }

    private fun <T : IRepository> putRepository(clazz: Class<T>?, repository: IRepository?): RepositoryFactory {
        if (clazz == null) {
            throw NullPointerException("repository class can not be null: ")
        }
        mRepoPools[clazz] = repository
        return this
    }

    private fun <T : IRepository> createRepository(clazz: Class<T>): T {
        try {
            return clazz.newInstance()
        } catch (e: InstantiationException) {
            throw RuntimeException("Cannot create an instance of $clazz", e)
        } catch (e: IllegalAccessException) {
            throw RuntimeException("Cannot create an instance of $clazz", e)
        }

    }

}