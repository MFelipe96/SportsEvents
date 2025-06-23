package com.mypackage.sportsevents.presentation.viewmodel

import com.mypackage.sportsevents.domain.model.Sport

sealed class SportsUiState {
    object Loading : SportsUiState()
    data class Success(val sports: List<Sport>) : SportsUiState()
    data class Error(val message: String) : SportsUiState()
}