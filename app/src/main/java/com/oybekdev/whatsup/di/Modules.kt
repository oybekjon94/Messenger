package com.oybekdev.whatsup.di

import com.github.terrakok.cicerone.Cicerone
import com.oybekdev.data.local.settings.SettingsRealm
import com.oybekdev.data.local.settings.SettingsStorage
import com.oybekdev.data.local.settings.SettingsStorageImpl
import com.oybekdev.data.local.user.UserStorage
import com.oybekdev.data.local.user.UserStorageImpl
import com.oybekdev.data.remote.auth.AuthFirebase
import com.oybekdev.data.remote.auth.AuthFirebaseImpl
import com.oybekdev.data.remote.users.UsersFirestore
import com.oybekdev.data.remote.users.UsersFirestoreImpl
import com.oybekdev.data.repo.AuthRepositoryImpl
import com.oybekdev.data.repo.SettingsRepositoryImpl
import com.oybekdev.domain.model.ActivityHolder
import com.oybekdev.domain.repo.AuthRepository
import com.oybekdev.domain.repo.SettingsRepository
import com.oybekdev.domain.usecase.auth.SendSmsCodeUseCase
import com.oybekdev.domain.usecase.auth.VerifyCodeUseCase
import com.oybekdev.domain.usecase.settings.GetInitialScreenUseCase
import com.oybekdev.domain.usecase.settings.OnboardedUseCase
import com.oybekdev.presentation.screens.code.CodeViewModel
import com.oybekdev.presentation.screens.main.MainViewModel
import com.oybekdev.presentation.screens.onboarding.OnboardingViewModel
import com.oybekdev.presentation.screens.phone.PhoneViewModel
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val cicerone = Cicerone.create()

//open realm
val config = RealmConfiguration.Builder(schema = setOf(SettingsRealm::class))
    .build()

val appModule = module {
    single { cicerone.router }
    single { cicerone.getNavigatorHolder() }
    single { Realm.open(config) }
    single { ActivityHolder() }
}

val repositoryModule = module {
    single <AuthRepository> { AuthRepositoryImpl(get(), get()) }
    single <SettingsRepository> { SettingsRepositoryImpl(get())  }
}

val useCaseModule = module {
    single { SendSmsCodeUseCase(get()) }
    single { OnboardedUseCase(get()) }
    single { GetInitialScreenUseCase(get(),get()) }
    single { VerifyCodeUseCase(get()) }

}

val localModule = module {
    single <UserStorage>{ UserStorageImpl() }
    single <SettingsStorage>{ SettingsStorageImpl(get()) }
}

val remoteModule = module {
    single <AuthFirebase> { AuthFirebaseImpl(get()) }
    single <UsersFirestore> { UsersFirestoreImpl() }

}

val viewModelModule = module {
    viewModel{ PhoneViewModel(get(),get()) }
    viewModel{MainViewModel(get(),get())}
    viewModel{OnboardingViewModel(get(), get())}
    viewModel{CodeViewModel(get(), get())}
}
