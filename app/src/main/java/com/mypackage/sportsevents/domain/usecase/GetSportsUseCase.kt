package com.mypackage.sportsevents.domain.usecase

import com.mypackage.sportsevents.domain.model.Sport
import com.mypackage.sportsevents.domain.repository.SportsRepository
import javax.inject.Inject

class GetSportsUseCase @Inject constructor(
    private val repository: SportsRepository
) {
    suspend operator fun invoke(): List<Sport> {
        return repository.getAllSports()
    }
}