package com.mypackage.sportsevents.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mypackage.sportsevents.domain.model.Event
import com.mypackage.sportsevents.domain.usecase.FilterFavoriteEventsUseCase
import com.mypackage.sportsevents.domain.usecase.GetSportsUseCase
import com.mypackage.sportsevents.domain.usecase.UpdateFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SportsViewModel @Inject constructor(
    private val getSportsUseCase: GetSportsUseCase,
    private val updateFavoriteUseCase: UpdateFavoriteUseCase,
    private val filterFavoriteEventsUseCase: FilterFavoriteEventsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<SportsUiState>(SportsUiState.Loading)
    val uiState: StateFlow<SportsUiState> = _uiState

    init {
        loadSports()
    }

    private fun loadSports() {
        viewModelScope.launch {
            _uiState.value = SportsUiState.Loading
            try {
                val result = getSportsUseCase()
                _uiState.value = SportsUiState.Success(result)
            } catch (e: Exception) {
                _uiState.value = SportsUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun toggleFavorite(eventId: String) {
        viewModelScope.launch {
            val currentState = _uiState.value
            if (currentState is SportsUiState.Success) {
                val updatedSports = currentState.sports.map { sport ->
                    val updatedEvents = sport.events.map { event ->
                        if (event.id == eventId) {
                            updateFavoriteUseCase(event.id, !event.isFavorite)
                            event.copy(isFavorite = !event.isFavorite)
                        } else {
                            event
                        }
                    }
                    sport.copy(events = updatedEvents)
                }
                _uiState.value = SportsUiState.Success(updatedSports)
            }
        }
    }

    fun filterByFavorites(events: List<Event>) = filterFavoriteEventsUseCase.invoke(events)

}

