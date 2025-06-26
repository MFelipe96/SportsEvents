package com.mypackage.sportsevents.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mypackage.sportsevents.R
import com.mypackage.sportsevents.domain.model.Sport
import com.mypackage.sportsevents.presentation.ui.components.SportSection
import com.mypackage.sportsevents.presentation.viewmodel.SportsUiState
import com.mypackage.sportsevents.presentation.viewmodel.SportsViewModel

@Composable
fun SportsScreen(
    viewModel: SportsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        is SportsUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is SportsUiState.Success -> {
            val sports = (uiState as SportsUiState.Success).sports
            if (sports.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(stringResource(R.string.no_events_available_error_msg))
                }
            } else {
                SportsContent(sports = sports, viewModel = viewModel)
            }
        }

        is SportsUiState.Error -> {
            val message = (uiState as SportsUiState.Error).message
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { viewModel.loadSports() }) {
                    Text(color = MaterialTheme.colorScheme.onTertiary, text = stringResource(R.string.try_again_button))
                }
            }
        }
    }
}

@Composable
fun SportsContent(sports: List<Sport>, viewModel: SportsViewModel) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(sports) { sport ->
            var showOnlyFavorites by remember { mutableStateOf(false) }

            val filteredEvents = remember(showOnlyFavorites, sport.events) {
                if (showOnlyFavorites)
                    viewModel.filterByFavorites(sport.events)
                else sport.events
            }

            SportSection(
                sport = sport,
                events = filteredEvents,
                isFavoriteFilterEnabled = showOnlyFavorites,
                onFavoriteToggleChange = { showOnlyFavorites = it },
                onFavoriteClick = { eventId ->
                    viewModel.toggleFavorite(eventId)
                }
            )
        }
    }
}
