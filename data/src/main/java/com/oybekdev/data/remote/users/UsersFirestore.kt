package com.oybekdev.data.remote.users

import com.google.firebase.auth.FirebaseUser
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface UsersFirestore {
    fun saveUser(user:FirebaseUser):Completable
    fun getUsers(): Single<List<UserDocument>>
}