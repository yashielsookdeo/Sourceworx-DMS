# Dashboard Screen

## Overview

The Dashboard Screen is the main screen of the SourceworxDMS application after login. It provides an overview of tasks, notifications, and quick access to key features.

## UI Components

### Header Section
- **Welcome Message**: Personalized greeting with user name
- **Notification Icons**:
  - Warning icon (yellow) - Navigates to Report Problem screen
  - Notification icon (red) with badge - Navigates to Alerts screen

### Quick Action Buttons
- **Manage Profile**: Button for profile management
- **View Stats**: Button for viewing statistics
- **My Tasks**: Full-width button to navigate to Tasks to Scan screen

### Task Sections
- **New Tasks**: Section displaying new tasks assigned to the user
- **Rejected Tasks**: Section displaying tasks that were rejected

### Task Cards
- **Task Card**: Displays case number, location, priority, due date, and accept button
- **Rejected Task Card**: Displays case number, location, priority, due date, rejection reason, affected pages, and re-scan button

## Features

### Navigation
- Navigation to Alerts screen
- Navigation to Report Problem screen
- Navigation to Scan Tasks screen
- Navigation to Tasks to Scan screen

### Task Management
- View new tasks
- Accept tasks
- View rejected tasks
- Re-scan rejected documents

## Implementation Details

The Dashboard Screen is implemented in `DashboardScreen.kt` using Jetpack Compose. Key implementation details include:

- Scrollable content for handling many tasks
- Custom card components for different task types
- Action buttons with icons for quick access to features
- Notification badge showing unread notifications count

## Design

- Clean, card-based design with consistent spacing
- Color-coded elements for different priorities and statuses
- Responsive layout that adapts to different screen sizes
- Visual hierarchy emphasizing important information

## Usage

```kotlin
DashboardScreen(
    onNavigateToAlerts = { /* Navigate to Alerts */ },
    onNavigateToScanTasks = { /* Navigate to Scan Tasks */ },
    onNavigateToTasksToScan = { /* Navigate to Tasks to Scan */ },
    onNavigateToReportProblem = { /* Navigate to Report Problem */ }
)
```

## Preview

The screen includes preview functions for development purposes:

```kotlin
@Preview(showBackground = true, showSystemUi = true, device = "spec:width=411dp,height=891dp")
@Composable
fun DashboardScreenPreview() {
    SourceworxDMSTheme {
        DashboardScreen(
            onNavigateToAlerts = {},
            onNavigateToScanTasks = {},
            onNavigateToTasksToScan = {},
            onNavigateToReportProblem = {}
        )
    }
}
```

## Notes

- The dashboard is designed to match the design in dashboard 1.png from the assets folder
- Task data is currently hardcoded for demonstration purposes
- In a production environment, tasks would be fetched from a backend service
