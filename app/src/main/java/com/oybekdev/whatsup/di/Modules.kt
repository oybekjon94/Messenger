package com.oybekdev.whatsup.di

import com.android.volley.toolbox.Volley
import com.github.terrakok.cicerone.Cicerone
import com.oybekdev.data.local.settings.SettingsRealm
import com.oybekdev.data.local.settings.SettingsStorage
import com.oybekdev.data.local.settings.SettingsStorageImpl
import com.oybekdev.data.local.user.UserStorage
import com.oybekdev.data.local.user.UserStorageImpl
import com.oybekdev.data.remote.auth.AuthFirebase
import com.oybekdev.data.remote.auth.AuthFirebaseImpl
import com.oybekdev.data.remote.files.ImagesStorage
import com.oybekdev.data.remote.files.ImagesStorageImpl
import com.oybekdev.data.remote.messages.MessagesFirestore
import com.oybekdev.data.remote.messages.MessagesFirestoreImpl
import com.oybekdev.data.remote.push.PushVolley
import com.oybekdev.data.remote.push.PushVolleyImpl
import com.oybekdev.data.remote.users.UsersFirestore
import com.oybekdev.data.remote.users.UsersFirestoreImpl
import com.oybekdev.data.repo.AuthRepositoryImpl
import com.oybekdev.data.repo.CharRepositoryImpl
import com.oybekdev.data.repo.SettingsRepositoryImpl
import com.oybekdev.domain.model.ActivityHolder
import com.oybekdev.domain.repo.AuthRepository
import com.oybekdev.domain.repo.ChatRepository
import com.oybekdev.domain.repo.SettingsRepository
import com.oybekdev.domain.usecase.auth.SendSmsCodeUseCase
import com.oybekdev.domain.usecase.auth.VerifyCodeUseCase
import com.oybekdev.domain.usecase.chat.GetChatsUseCase
import com.oybekdev.domain.usecase.chat.GetMessageUseCase
import com.oybekdev.domain.usecase.chat.SendImageUseCase
import com.oybekdev.domain.usecase.chat.SendMessageUseCase
import com.oybekdev.domain.usecase.settings.GetInitialScreenUseCase
import com.oybekdev.domain.usecase.settings.OnboardedUseCase
import com.oybekdev.presentation.screens.chat.ChatViewModel
import com.oybekdev.presentation.screens.code.CodeViewModel
import com.oybekdev.presentation.screens.home.HomeViewModel
import com.oybekdev.presentation.screens.main.MainViewModel
import com.oybekdev.presentation.screens.onboarding.OnboardingViewModel
import com.oybekdev.presentation.screens.phone.PhoneViewModel
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.koin.android.ext.koin.androidContext
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
    single {Volley.newRequestQueue(androidContext())}
}

val repositoryModule = module {
    single <AuthRepository> { AuthRepositoryImpl(get(), get()) }
    single <SettingsRepository> { SettingsRepositoryImpl(get())  }
    single <ChatRepository> { CharRepositoryImpl(get(),get(),get(),get(),get())  }
}

val useCaseModule = module {
    single { SendSmsCodeUseCase(get()) }
    single { OnboardedUseCase(get()) }
    single { GetInitialScreenUseCase(get(),get()) }
    single { VerifyCodeUseCase(get()) }
    single { GetChatsUseCase(get()) }
    single { GetMessageUseCase(get()) }
    single { SendMessageUseCase(get()) }
    single { SendImageUseCase(get()) }
}

val localModule = module {
    single <UserStorage>{ UserStorageImpl() }
    single <SettingsStorage>{ SettingsStorageImpl(get()) }
}

val remoteModule = module {
    single <AuthFirebase> { AuthFirebaseImpl(get()) }
    single <UsersFirestore> { UsersFirestoreImpl() }
    single <MessagesFirestore> { MessagesFirestoreImpl() }
    single <ImagesStorage> { ImagesStorageImpl() }
    single <PushVolley> { PushVolleyImpl(get()) }
}

val viewModelModule = module {
    viewModel{ PhoneViewModel(get(),get()) }
    viewModel{MainViewModel(get(),get())}
    viewModel{OnboardingViewModel(get(), get())}
    viewModel{CodeViewModel(get(), get())}
    viewModel{HomeViewModel( get(),get())}
    viewModel{ChatViewModel(get(),get(),get())}
}
