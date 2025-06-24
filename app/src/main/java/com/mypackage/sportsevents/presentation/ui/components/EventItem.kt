package com.mypackage.sportsevents.presentation.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarRate
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mypackage.sportsevents.domain.model.Event
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

@Composable
fun EventItem(
    event: Event,
    onFavoriteClick: () -> Unit
) {
    var remainingTime by remember { mutableLongStateOf(event.timestamp - (System.currentTimeMillis() / 1000)) }

    LaunchedEffect(event.id) {
        while (remainingTime > 0) {
            delay(1000L)
            remainingTime--
        }
    }

    val formattedTime = formatTime(remainingTime)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                val competitors = event.name.split("-").map { it.trim() }
                if (competitors.size == 2) {
                    Text("${competitors[0]} vs ${competitors[1]}", style = MaterialTheme.typography.bodyLarge)
                } else {
                    Text(event.name, style = MaterialTheme.typography.bodyLarge)
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Starts in: $formattedTime",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            IconButton(onClick = onFavoriteClick) {
                Icon(
                    imageVector = if (event.isFavorite) Icons.Filled.Star else Icons.Outlined.StarRate,
                    contentDescription = "Toggle favorite",
                    tint = if (event.isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

private fun formatTime(seconds: Long): String {
    val h = TimeUnit.SECONDS.toHours(seconds)
    val m = TimeUnit.SECONDS.toMinutes(seconds) % 60
    val s = seconds % 60
    return String.format("%02dh:%02dm:%02ds", h, m, s)
}

@Preview(showBackground = true)
@Composable
fun EventItemPreview() {
    val mockEvent = Event(
        id = "123",
        name = "Brazil - Argentina",
        timestamp = (System.currentTimeMillis() / 1000) + 3600, // +1 hora
        isFavorite = true
    )

    MaterialTheme {
        EventItem(
            event = mockEvent,
            onFavoriteClick = {}
        )
    }
}