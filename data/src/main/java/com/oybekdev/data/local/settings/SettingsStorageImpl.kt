package com.oybekdev.data.local.settings

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query

class SettingsStorageImpl(
    private val realm: Realm,
) : SettingsStorage {

    private fun getSettings(): Single<SettingsRealm> = Single.fromCallable {
        realm.query<SettingsRealm>().find().firstOrNull() ?: run {
            realm.writeBlocking {
                copyToRealm(SettingsRealm())
            }
        }
    }

    override fun onboarded(): Completable = getSettings().flatMapCompletable {
        Completable.fromCallable {
            realm.writeBlocking {
                copyToRealm(it.copy(onboarded = true), UpdatePolicy.ALL)
            }
        }
    }


    override fun getBoarded(): Single<Boolean> = getSettings().map { it.onboarded }
}