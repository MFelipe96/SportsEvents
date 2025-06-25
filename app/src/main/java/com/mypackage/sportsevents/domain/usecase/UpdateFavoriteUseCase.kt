package com.mypackage.sportsevents.domain.usecase

import com.mypackage.sportsevents.domain.repository.SportsRepository
import javax.inject.Inject

class UpdateFavoriteUseCase @Inject constructor(
    private val repository: SportsRepository
) {
    suspend operator fun invoke(eventId: String, isFavorite: Boolean) {
        repository.updateFavoriteStatus(eventId, isFavorite)
    }
}