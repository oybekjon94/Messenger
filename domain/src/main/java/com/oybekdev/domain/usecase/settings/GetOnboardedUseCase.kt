package com.oybekdev.domain.usecase.settings

import com.oybekdev.domain.repo.SettingsRepository

class GetOnboardedUseCase(
    private val settingsRepository: SettingsRepository
) {

    operator fun invoke() = settingsRepository.getOnboarded()
}