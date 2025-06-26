package com.mypackage.sportsevents.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mypackage.sportsevents.R
import com.mypackage.sportsevents.domain.model.Event
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

@Composable
fun EventItem(
    event: Event,
    onFavoriteClick: () -> Unit
) {
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
                    buildCompetitorsLayout(firstCompetitor = competitors[0], secondCompetitor = competitors[1])
                } else {
                    Text(event.name, style = MaterialTheme.typography.bodyLarge)
                }
                Spacer(modifier = Modifier.height(4.dp))
                EventCountdown(event.timestamp)
            }

            IconButton(onClick = onFavoriteClick) {
                Icon(
                    imageVector = if (event.isFavorite) Icons.Filled.Star else Icons.Outlined.StarOutline,
                    contentDescription = "Toggle favorite",
                    tint = if (event.isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}
@Composable
fun buildCompetitorsLayout(firstCompetitor: String, secondCompetitor: String){
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { showDialog = true }
    ) {
        Text(
            text = firstCompetitor,
            style = MaterialTheme.typography.bodyLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "vs",
            style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.primary)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = secondCompetitor,
            style = MaterialTheme.typography.bodyLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(stringResource(R.string.match_details_title)) },
            text = { Text("$firstCompetitor vs $secondCompetitor") },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text(stringResource(R.string.ok_msg))
                }
            }
        )
    }

}
@Composable
fun EventCountdown(eventTimestamp: Long) {
    var now by remember { mutableLongStateOf(System.currentTimeMillis() / 1000) }

    LaunchedEffect(eventTimestamp) {
        if (eventTimestamp > now) {
            while (true) {
                now = System.currentTimeMillis() / 1000
                delay(1000L)
            }
        }
    }

    val diff = eventTimestamp - now

    val displayText = if (diff > 0) {
        "Starts in ${formatTime(diff)}"
    } else {
        "Started at ${formatDateTime(eventTimestamp)}"
    }

    Text(
        text = displayText,
        style = MaterialTheme.typography.bodyMedium,
        color = if (diff > 0) MaterialTheme.colorScheme.secondary else Color.Red
    )
}

private fun formatDateTime(timestamp: Long): String {
    val sdf = SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault())
    val date = Date(timestamp * 1000)
    return sdf.format(date)
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
        timestamp = (System.currentTimeMillis() / 1000) + 3600,
        isFavorite = true
    )

    MaterialTheme {
        EventItem(
            event = mockEvent,
            onFavoriteClick = {}
        )
    }
}