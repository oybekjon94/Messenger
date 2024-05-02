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

    override fun onboarded(): Completable = Completable.fromCallable {
        realm.writeBlocking {
            query<SettingsRealm>().find().firstOrNull()?.onboarded = true
        }
    }

    override fun getOnboarded(): Single<Boolean> = getSettings().map { it.onboarded }
}