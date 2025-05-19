# Tasks to Scan Screen

## Overview

The Tasks to Scan Screen displays a list of documents that are assigned to the user for scanning. It provides filtering options and detailed information about each task.

## UI Components

### Top App Bar
- **Title**: "Tasks to Scan"
- **Back Button**: Icon button to navigate back

### Tab Bar
- **Tabs**: Tabs for filtering tasks (All Tasks, Urgent, Today, This Week)

### Task List
- **Task Items**: Cards displaying task details with actions

## Features

### Task Management
- View list of tasks assigned for scanning
- Filter tasks by category (All, Urgent, Today, This Week)
- View task details including priority, due date, and document type
- Perform actions on tasks (View details, Start scanning)

### Task Priority
- Low (Green)
- Medium (Amber)
- High (Red)

## Implementation Details

The Tasks to Scan Screen is implemented in `TasksToScanScreen.kt` using Jetpack Compose. Key implementation details include:

- Tab layout for filtering tasks
- List of tasks with details
- Color-coded priority indicators
- Action buttons for each task

## Design

- Material Design 3 components
- Color-coded priority indicators:
  - Green for Low priority
  - Amber for Medium priority
  - Red for High priority
- Card-based layout for tasks with clear information hierarchy

## Usage

```kotlin
TasksToScanScreen(
    onBackClick = { /* Navigate back */ }
)
```

## Preview

The screen includes a preview function for development purposes:

```kotlin
@Preview(showBackground = true)
@Composable
fun TasksToScanScreenPreview() {
    SourceworxDMSTheme {
        TasksToScanScreen(onBackClick = {})
    }
}
```

## Notes

- Task data is currently hardcoded for demonstration purposes
- In a production environment, tasks would be fetched from a backend service
- The screen is accessible from the dashboard by clicking the "My Tasks" button
