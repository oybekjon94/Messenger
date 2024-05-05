package com.oybekdev.presentation.base

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject

abstract class BaseViewModel<State : Any, Input:Any, Effect:Any> : ViewModel() {

    private val stateSubject = BehaviorSubject.createDefault(this.getDefaultState())
    //for observe
    val state : Observable<State>get() = stateSubject
        .distinctUntilChanged()
        .subscribeOn(Schedulers.computation()) //related processor
        .observeOn(AndroidSchedulers.mainThread())

    val current:State get() = stateSubject.blockingFirst()

    //for effect
    private val effectsSubject = PublishSubject.create<Effect>()
    val effects: Observable<Effect>get() = effectsSubject

    abstract fun getDefaultState():State

    //for update
    fun updateState(update: (current:State) -> State){
        val state = update(stateSubject.blockingFirst())
        stateSubject.onNext(state)
    }

    //for input
    abstract fun processInput(input: Input)

    fun emitEffect(effect: Effect){
        effectsSubject.onNext(effect)
    }
}