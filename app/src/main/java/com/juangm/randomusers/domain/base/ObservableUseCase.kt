package com.juangm.randomusers.domain.base

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

abstract class ObservableUseCase<T, Params> : BaseUseCase<T>() {

    abstract fun useCaseExecution(params: Params): Observable<T>

    fun execute(onNext: (T) -> Unit, onError: (Throwable) -> Unit = {}, params: Params) {
        val observable = useCaseExecution(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
        val disposable = observable
            .subscribeWith(disposableObserver(onNext, onError))
        disposables.add(disposable)
    }
}