package com.oybekdev.data.local.settings

import io.realm.kotlin.types.ObjectId
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class SettingsRealm: RealmObject {
    @PrimaryKey
    var id: ObjectId = ObjectId.create()
    var onboarded: Boolean = false
}
