# Report Problem Screen

## Overview

The Report Problem Screen allows users to report issues with documents or the system. It provides a structured way to select a task, categorize the problem, and provide additional details.

## UI Components

### Top App Bar
- **Title**: "Report Problem"
- **Close Button**: Icon button to navigate back

### Task Selection
- **Task Dropdown**: Dropdown menu to select the task with an issue

### Problem Category
- **Category Grid**: Grid of problem categories with icons
- **Category Items**: Selectable cards for different problem types

### Problem Description
- **Description Field**: Text field for detailed problem description

### Priority and Due Date
- **Priority Display**: Shows the priority level of the selected task
- **Due Date Display**: Shows the due date of the selected task

### Action Button
- **Log Problem Button**: Button to submit the problem report

### Success Dialog
- **Confirmation Dialog**: Dialog shown after successful submission

## Features

### Problem Reporting
- Select a task from a dropdown list
- Choose a problem category from a visual grid
- Provide detailed description of the issue
- View task priority and due date
- Submit problem report

### Problem Categories
- Document Quality
- Missing Pages
- Wrong Document
- System Error
- Scanner Issue
- Other

## Implementation Details

The Report Problem Screen is implemented in `ReportProblemScreen.kt` using Jetpack Compose. Key implementation details include:

- Dropdown menu for task selection
- Grid layout for problem categories
- Text field for problem description
- Success dialog with confirmation message

## Design

- Clean, organized layout with clear sections
- Visual grid of problem categories with icons
- Color-coded priority indicator
- Consistent button styling with the application theme

## Usage

```kotlin
ReportProblemScreen(
    onBackClick = { /* Navigate back */ }
)
```

## Preview

The screen includes preview functions for development purposes:

```kotlin
@Preview(showBackground = true)
@Composable
fun ReportProblemScreenPreview() {
    SourceworxDMSTheme {
        ReportProblemScreen(onBackClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryItemPreview() {
    SourceworxDMSTheme {
        CategoryItem(
            category = ProblemCategory(
                id = 1,
                name = "Document Quality",
                icon = { Icon(Icons.Default.Image, contentDescription = null, tint = Color.White) }
            ),
            isSelected = true,
            onClick = {}
        )
    }
}
```

## Notes

- Task data is currently hardcoded for demonstration purposes
- In a production environment, tasks would be fetched from a backend service
- The screen is accessible from the dashboard by clicking the warning icon
