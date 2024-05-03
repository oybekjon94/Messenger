package com.oybekdev.data.remote.auth

import android.app.Activity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.oybekdev.domain.model.ActivityHolder
import com.oybekdev.domain.model.InvalidCredentialsException
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import java.util.concurrent.TimeUnit

class AuthFirebaseImpl(
    private val activityHolder: ActivityHolder
):AuthFirebase {

    private val auth = FirebaseAuth.getInstance()

    lateinit var verificationId:String
    lateinit var token:ForceResendingToken
    override fun sendSmsCode(phone: String):Completable= Completable.create {

        val callbacks = object :PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {

            }

            override fun onVerificationFailed(e: FirebaseException) {
                it.onError(e)
            }

            override fun onCodeSent(verificationId: String, token: ForceResendingToken) {
                this@AuthFirebaseImpl.verificationId = verificationId
                this@AuthFirebaseImpl.token = token
                it.onComplete()
            }
        }

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phone) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(activityHolder.activity)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    override fun verify(code: String): Single<FirebaseUser> = Single.create {
        val credential = PhoneAuthProvider.getCredential(verificationId, code)
        auth.signInWithCredential(credential)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful && isLoggedIn) {
                    it.onSuccess(auth.currentUser!!)
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        it.onError(InvalidCredentialsException())
                    }
                    // Update UI
                }
            }
    }

    override val isLoggedIn: Boolean
        get() = auth.currentUser != null


}