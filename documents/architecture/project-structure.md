# Project Structure and Architecture

## Overview

The SourceworxDMS application follows a modern Android architecture using Jetpack Compose for UI and a structured package organization.

## Project Structure

```
app/src/main/java/com/skyner/sourceworxdms/
├── MainActivity.kt                 # Application entry point
├── navigation/                     # Navigation components
│   ├── NavGraph.kt                 # Navigation graph definition
│   └── Screen.kt                   # Screen route definitions
└── ui/                             # UI components
    ├── components/                 # Reusable UI components
    │   ├── Buttons.kt              # General button components
    │   └── SureScanButton.kt       # Primary button component
    ├── screens/                    # Application screens
    │   ├── alerts/                 # Alerts screen
    │   │   └── AlertsScreen.kt
    │   ├── dashboard/              # Dashboard screen
    │   │   └── DashboardScreen.kt
    │   ├── login/                  # Login screen
    │   │   └── LoginScreen.kt
    │   ├── problem/                # Report Problem screen
    │   │   └── ReportProblemScreen.kt
    │   └── scan/                   # Scan-related screens
    │       ├── ScanTasksScreen.kt
    │       └── TasksToScanScreen.kt
    └── theme/                      # Theme definitions
        ├── Color.kt                # Color definitions
        ├── Theme.kt                # Theme configuration
        └── Type.kt                 # Typography definitions
```

## Architecture Components

### Main Activity

The `MainActivity` is the entry point of the application and sets up the Compose UI:

```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SourceworxDMSTheme {
                SourceworxDMSApp()
            }
        }
    }
}
```

### Navigation

The navigation system uses Jetpack Navigation Component with Compose:

- `Screen.kt` - Defines screen routes
- `NavGraph.kt` - Sets up the navigation graph

### UI Components

The UI is built with Jetpack Compose:

- **Screens**: Each screen is a composable function in its own file
- **Components**: Reusable UI components like buttons
- **Theme**: Defines colors, typography, and theme configuration

## Dependencies

The application uses the following key dependencies:

```gradle
dependencies {
    // AndroidX Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Navigation
    implementation(libs.androidx.navigation.compose)

    // Icons
    implementation(libs.androidx.material.icons.core)
    implementation(libs.androidx.material.icons.extended)

    // Image loading
    implementation(libs.coil.compose)

    // Kotlin extensions
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.datetime)
}
```

## Build Configuration

The application is configured with:

- Kotlin DSL build scripts
- Version catalog for dependency management
- Compose compiler plugin
- Kotlin serialization plugin

## Architecture Patterns

The application follows these architecture patterns:

- **Single Activity**: One activity with multiple composable screens
- **Composable Functions**: UI built with composable functions
- **State Hoisting**: State is hoisted to appropriate levels
- **Unidirectional Data Flow**: Data flows down, events flow up

## Notes

- The application is built with Jetpack Compose, which uses a declarative UI paradigm
- Navigation is handled through the Navigation Component
- The UI follows Material Design 3 guidelines
- The project structure is organized by feature
