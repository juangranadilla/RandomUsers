package com.juangm.randomusers.domain.base

import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

abstract class CompletableUseCase<T, Params> : BaseUseCase<T>() {

    abstract fun useCaseExecution(params: Params): Completable

    fun execute(onComplete: () -> Unit, onError: (Throwable) -> Unit = {}, params: Params) {
        val completable = useCaseExecution(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
        val disposable = completable
            .subscribeWith(disposableCompletableObserver(onComplete, onError))
        disposables.add(disposable)
    }
}