package com.juangm.randomusers.domain.base

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

abstract class ObservableUseCase<T, Params> : BaseUseCase<T>() {

    abstract fun buildUseCase(params: Params): Observable<T>

    fun execute(onComplete: () -> Unit, onNext: (T) -> Unit, onError: (Throwable) -> Unit = {}, params: Params) {
        val single = buildUseCase(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
        val disposable = single
            .subscribeWith(disposableObserver(onComplete, onNext, onError))
        disposables.add(disposable)
    }
}