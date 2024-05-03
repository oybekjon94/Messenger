package com.oybekdev.data.remote.users

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import io.reactivex.rxjava3.core.Completable

class UsersFirestoreImpl:UsersFirestore {

    private val db = Firebase.firestore

    override fun saveUser(user: FirebaseUser):Completable = Completable.create {emitter ->

        val data = hashMapOf(
            "id" to user.uid,
            "phone" to user.phoneNumber,
            "name" to "AnonymousUser",
            "avatar" to null
        )

        db.collection(collection).document(user.uid).set(data).addOnFailureListener{
            emitter.onError(it)
        }.addOnSuccessListener {
            emitter.onComplete()
        }
    }

    companion object{
        private const val collection = "users"
    }
}