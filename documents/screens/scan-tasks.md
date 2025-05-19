# Scan Tasks Screen

## Overview

The Scan Tasks Screen displays a list of documents that need to be scanned, are in progress, or have been completed. It provides a summary of task statuses and allows users to view and manage scanning tasks.

## UI Components

### Top App Bar
- **Title**: "Scan Tasks"
- **Back Button**: Icon button to navigate back
- **Search Icon**: Icon button for search functionality
- **Filter Icon**: Icon button for filtering tasks

### Status Summary
- **Status Cards**: Cards showing counts for Pending, In Progress, and Completed tasks

### Task List
- **Section Title**: "Recent Tasks"
- **Task Items**: Cards displaying task details

### Floating Action Button
- **Add Button**: Button to start a new scan

## Features

### Task Management
- View task status summary
- View list of scan tasks with details
- Filter and search tasks (UI only, not implemented)
- Start new scan (UI only, not implemented)

### Task Status
- Pending
- In Progress
- Completed
- Rejected

## Implementation Details

The Scan Tasks Screen is implemented in `ScanTasksScreen.kt` using Jetpack Compose. Key implementation details include:

- Status summary cards with counts
- List of tasks with details
- Color-coded status indicators
- Floating action button for new scans

## Design

- Material Design 3 components
- Color-coded status indicators:
  - Amber for Pending
  - Blue for In Progress
  - Green for Completed
  - Red for Rejected
- Card-based layout for tasks and status summary

## Usage

```kotlin
ScanTasksScreen(
    onBackClick = { /* Navigate back */ }
)
```

## Preview

The screen includes a preview function for development purposes:

```kotlin
@Preview(showBackground = true)
@Composable
fun ScanTasksScreenPreview() {
    SourceworxDMSTheme {
        ScanTasksScreen(onBackClick = {})
    }
}
```

## Notes

- Task data is currently hardcoded for demonstration purposes
- In a production environment, tasks would be fetched from a backend service
- The screen is designed to match the design in scan tasks.png and scan tasks 2.png from the assets folder
