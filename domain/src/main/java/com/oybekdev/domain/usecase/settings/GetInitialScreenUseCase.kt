package com.oybekdev.domain.usecase.settings

import com.oybekdev.domain.repo.AuthRepository
import com.oybekdev.domain.repo.SettingsRepository
import io.reactivex.rxjava3.core.Single

class GetInitialScreenUseCase(
    private val settingsRepository: SettingsRepository,
    private val authRepository: AuthRepository,
) {

    operator fun invoke(): Single<Result> = settingsRepository.getOnboarded().map { onboarded ->
        return@map when {
            authRepository.isLoggedIn -> Result.Home
            onboarded -> Result.Phone
            else -> Result.Onboarding
        }
    }

    sealed class Result {
        object Onboarding : Result()
        object Phone : Result()
        object Home : Result()
    }
}