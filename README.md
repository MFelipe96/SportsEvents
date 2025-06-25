# Sports Events App

An Android application that displays a list of upcoming sports events grouped by sport type, with real-time countdown, filtering by favorites, and offline support via local database caching.

## Features

- Displays a list of sports and events grouped by sport type
- Button to mark events as favorite
- Dialog to display full event name when text is truncated
- Favorite filter toggle per sport
- Real-time countdown timer showing time left until each event starts
- Displays past events with the date/time they occurred
- Sorts events by start time (upcoming events first)
- Sorts sports alphabetically
- Favorites and event data persisted locally using Room
- Full offline support when there's no internet connection
- Proper error messages shown when network fails or no events are available

## Architecture

The app follows the Clean Architecture pattern with a clear separation of concerns across the following layers:

- **Presentation Layer**: Jetpack Compose UI, StateFlow for reactive state handling, and ViewModels injected via Hilt.
- **Domain Layer**: UseCases, models and repository.
- **Data Layer**: Remote API access via Retrofit, and local persistence via Room.

Used MVVM for a testable and maintainable architecture, and dependency injection with Hilt to simplify object creation and improve scalability.

## Testing

- A unit test was added to validate favorite event filtering logic.

## Execution

Compatible with:

- **Min SDK:** 21
- **Target SDK:** 33
- **Java:** 17
- **AGP:** 8.1.3
- **Gradle:** 8.0
- **Kotlin:** 1.8.10


## Technical Considerations

- The app uses StateFlow for reactive UI state management
- When a network error occurs, data is retrieved directly from Room cache
- Data is always synchronized with the API whenever possible
- The UI was redesigned to enhance the user experience while respecting the original challenge requirements
- Uses MaterialTheme.colorScheme with a dark theme tailored for betting and sports-related apps
- Full event names are shown in a dialog when the displayed text is truncated

## Future Improvements

- Add navigation to event detail screens
- Implement UI tests
- Improve unit test coverage
- Add animations for section expansion/collapse
- Add support for multiple languages
- Show a small “upcoming” countdown next to each sport title to indicate when the next event will start