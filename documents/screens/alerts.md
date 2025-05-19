# Alerts Screen

## Overview

The Alerts Screen displays notifications and alerts for the user, categorized by type. It provides a comprehensive view of resolved problems, overdue tasks, and rejected documents.

## UI Components

### Top App Bar
- **Title**: "Alerts"
- **Close Button**: Icon button to navigate back

### Alert Sections
- **Resolved Problems**: Section showing resolved issues
- **Overdue**: Section showing overdue tasks
- **Rejected**: Section showing rejected documents

### Alert Cards
- **Resolved Problem Card**: Displays case number, issue type, resolved date, and resolution
- **Overdue Card**: Displays case number and days overdue
- **Rejected Card**: Displays case number and priority

## Features

### Alert Management
- View resolved problems with resolution details
- View overdue tasks with days overdue
- View rejected documents with priority level

### Navigation
- Navigate back to previous screen

## Implementation Details

The Alerts Screen is implemented in `AlertsScreen.kt` using Jetpack Compose. Key implementation details include:

- Section headers with icons and counts
- Color-coded cards for different alert types
- Custom card components for each alert type

## Design

- Color-coded sections for different alert types:
  - Green for resolved problems
  - Amber/Orange for overdue tasks
  - Red for rejected documents
- Consistent card design with appropriate background colors
- Clear visual hierarchy with section headers

## Usage

```kotlin
AlertsScreen(
    onBackClick = { /* Navigate back */ }
)
```

## Preview

The screen includes a preview function for development purposes:

```kotlin
@Preview(showBackground = true, showSystemUi = true, device = "spec:width=411dp,height=891dp")
@Composable
fun AlertsScreenPreview() {
    SourceworxDMSTheme {
        AlertsScreen(onBackClick = {})
    }
}
```

## Notes

- Alert data is currently hardcoded for demonstration purposes
- In a production environment, alerts would be fetched from a backend service
- The screen is accessible from the dashboard by clicking the notification icon
