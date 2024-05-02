package com.oybekdev.domain.usecase.settings

import com.oybekdev.domain.repo.SettingsRepository

class OnboardedUseCase(
    private val settingsRepository: SettingsRepository
) {

    operator fun invoke() = settingsRepository.onboarded()
}