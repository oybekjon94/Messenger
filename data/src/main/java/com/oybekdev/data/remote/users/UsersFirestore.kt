package com.oybekdev.data.remote.users

import com.google.firebase.auth.FirebaseUser
import io.reactivex.rxjava3.core.Completable

interface UsersFirestore {
    fun saveUser(user:FirebaseUser):Completable
}