package com.mypackage.sportsevents.domain.usecase

import com.mypackage.sportsevents.domain.model.Sport
import com.mypackage.sportsevents.domain.repository.SportsRepository

class GetSportsUseCase(
    private val repository: SportsRepository
) {
    suspend operator fun invoke(): List<Sport> {
        return repository.getAllSports()
    }
}