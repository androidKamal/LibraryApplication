package com.androidkamallib.library.rx

import com.androidkamallib.library.base.BaseModel
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


class RxBus {

    private val bus = PublishSubject.create<BaseModel>()

    fun send(o: BaseModel) {
        bus.onNext(o)
    }

    fun toObservable(): Observable<BaseModel> {
        return bus
    }

}