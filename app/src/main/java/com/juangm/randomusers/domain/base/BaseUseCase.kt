package com.juangm.randomusers.domain.base

import android.util.Log
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableSingleObserver

abstract class BaseUseCase<T> : Disposable {

    protected var disposables = CompositeDisposable()
    private val TAG = BaseUseCase::class.qualifiedName

    protected fun disposableSingleObserver(
        onNext: (T) -> Unit,
        onError: (Throwable) -> Unit = {}
    ): DisposableSingleObserver<T> {

        return object : DisposableSingleObserver<T>() {
            override fun onSuccess(value: T) {
                onNext.invoke(value)
            }

            override fun onError(e: Throwable) {
                Log.e(TAG, "onError... ${e.localizedMessage}")
                onError.invoke(e)
            }
        }
    }

    protected fun disposableCompletableObserver(
        onComplete: () -> Unit,
        onError: (Throwable) -> Unit = {}
    ): DisposableCompletableObserver {

        return object : DisposableCompletableObserver() {

            override fun onComplete() {
                onComplete.invoke()
            }

            override fun onError(e: Throwable) {
                Log.e(TAG, "onError... ${e.localizedMessage}")
                onError.invoke(e)
            }
        }
    }

    override fun dispose() {
        disposables.clear()
    }

    override fun isDisposed(): Boolean {
        return disposables.isDisposed
    }
}