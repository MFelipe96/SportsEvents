package com.mypackage.sportsevents.presentation.ui.components

import androidx.compose.ui.Alignment
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Sports
import androidx.compose.material.icons.filled.SportsBar
import androidx.compose.material.icons.filled.SportsBasketball
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.SportsHandball
import androidx.compose.material.icons.filled.SportsHockey
import androidx.compose.material.icons.filled.SportsScore
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material.icons.filled.SportsTennis
import androidx.compose.material.icons.filled.SportsVolleyball
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mypackage.sportsevents.R
import com.mypackage.sportsevents.domain.model.Sport
import com.mypackage.sportsevents.domain.model.Event

@Composable
fun SportSection(
    sport: Sport,
    events: List<Event>,
    initiallyExpanded: Boolean = false,
    isFavoriteFilterEnabled: Boolean,
    onFavoriteToggleChange: (Boolean) -> Unit,
    onFavoriteClick: (eventId: String) -> Unit
) {
    var isExpanded by remember { mutableStateOf(initiallyExpanded) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { isExpanded = !isExpanded }
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = stringResource(R.string.expand_or_collapse_description)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = sport.name,
                style = MaterialTheme.typography.titleMedium
            )
            Icon(
                imageVector = getSportIcon(sport.id),
                contentDescription = null,
                modifier = Modifier
                    .size(28.dp)
                    .padding(4.dp),
            )
            Spacer(modifier = Modifier.weight(1f))
            ShowFavoritesButton(isFavoriteFilterEnabled, onFavoriteToggleChange)
        }
        Divider()
        AnimatedVisibility(visible = isExpanded) {
            Column(modifier = Modifier.fillMaxWidth()){
                if(events.isNotEmpty()) {
                    events.forEach { event ->
                        EventItem(
                            event = event,
                            onFavoriteClick = { onFavoriteClick(event.id) }
                        )
                    }
                }else
                    Text(
                        text = stringResource(R.string.no_favorite_events_msg),
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(16.dp)
                    )
            }
        }
    }
}

@Composable
fun ShowFavoritesButton(isFavoriteFilterEnabled: Boolean, onFavoriteToggleChange: (Boolean) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Switch(
            checked = isFavoriteFilterEnabled,
            onCheckedChange = onFavoriteToggleChange,
            thumbContent = {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = null,
                        modifier = Modifier.size(SwitchDefaults.IconSize),
                        tint = if (isFavoriteFilterEnabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
                    )
            }
        )
    }
}

fun getSportIcon(sportId: String): ImageVector {
    return when (sportId.lowercase()) {
        "foot", "futs" -> Icons.Default.SportsSoccer
        "bask" -> Icons.Default.SportsBasketball
        "tabl", "tenn" -> Icons.Default.SportsTennis
        "voll" -> Icons.Default.SportsVolleyball
        "esps" -> Icons.Default.SportsEsports
        "iceh" -> Icons.Default.SportsHockey
        "hand" -> Icons.Default.SportsHandball
        "snoo" -> Icons.Default.SportsBar
        "dart" -> Icons.Default.SportsScore
        else -> Icons.Default.Sports
    }
}

@Preview(showBackground = true)
@Composable
fun SportSectionPreview() {
    val mockEvents = listOf(
        Event(id = "1", name = "Spain - Italy", timestamp = (System.currentTimeMillis() / 1000) + 7200, isFavorite = false),
        Event(id = "2", name = "France - Germany", timestamp = (System.currentTimeMillis() / 1000) + 5400, isFavorite = true),
    )
    val mockSport = Sport(
        id = "FOOT",
        name = "SOCCER",
        events = mockEvents.toMutableList()
    )

    var favFilter by remember { mutableStateOf(false) }

    MaterialTheme {
        SportSection(
            sport = mockSport,
            events = if (favFilter) mockEvents.filter { it.isFavorite } else mockEvents,
            isFavoriteFilterEnabled = favFilter,
            onFavoriteToggleChange = { favFilter = it },
            onFavoriteClick = {}
        )
    }
}