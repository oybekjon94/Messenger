package com.oybekdev.data.repo

import com.oybekdev.data.remote.auth.AuthFirebase
import com.oybekdev.data.remote.users.UsersFirestore
import com.oybekdev.domain.repo.AuthRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers

class AuthRepositoryImpl constructor(
    private val authFirebase: AuthFirebase,
    private val usersFirestore: UsersFirestore
):AuthRepository {
    override fun sendSmsCode(phone: String) : Completable = authFirebase.sendSmsCode(phone)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    override fun verify(code: String): Completable = authFirebase.verify(code)
        .flatMapCompletable {
           usersFirestore.saveUser(it)
        }.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    override val isLoggedIn: Boolean
        get() = authFirebase.isLoggedIn
}