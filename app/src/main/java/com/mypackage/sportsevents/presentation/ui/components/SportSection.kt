package com.mypackage.sportsevents.presentation.ui.components

import androidx.compose.ui.Alignment
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
                contentDescription = "Expand or Collapse"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = sport.name,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.weight(1f))
            ShowFavoritesButton(isFavoriteFilterEnabled, onFavoriteToggleChange)
        }
        Divider()
        AnimatedVisibility(visible = isExpanded) {
            Column {
                events.forEach { event ->
                    EventItem(
                        event = event,
                        onFavoriteClick = { onFavoriteClick(event.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun ShowFavoritesButton(isFavoriteFilterEnabled: Boolean, onFavoriteToggleChange: (Boolean) -> Unit){
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = null,
            tint = if (isFavoriteFilterEnabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
        )
        Spacer(Modifier.width(4.dp))
        Switch(
            checked = isFavoriteFilterEnabled,
            onCheckedChange = onFavoriteToggleChange
        )
    }

}