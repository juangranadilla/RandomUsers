package com.juangm.randomusers.domain.base

import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

abstract class CompletableUseCase<T, Params>() : BaseUseCase<Void>() {

    abstract fun buildUseCase(params: Params): Completable

    fun execute(onCompleted: () -> Unit, onError: (Throwable?) -> Unit = {}, params: Params) {
        val completable = buildUseCase(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
        val disposable = completable
            .subscribeWith(disposableCompletableObserver(onCompleted, onError))
        disposables.add(disposable)
    }
}