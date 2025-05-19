# UI Components

## Overview

The SourceworxDMS application uses a set of reusable UI components to maintain consistent styling throughout the application. These components are implemented as composable functions.

## Button Components

### SureScanButton

Primary button used for main actions.

```kotlin
@Composable
fun SureScanButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = PaddingValues(vertical = 12.dp, horizontal = 16.dp)
)
```

### SourceworxButton

Standard button used for general actions.

```kotlin
@Composable
fun SourceworxButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = PaddingValues(vertical = 12.dp, horizontal = 16.dp)
)
```

## Card Components

### TaskCard

Card component for displaying task information on the Dashboard.

```kotlin
@Composable
fun TaskCard(
    caseNumber: String,
    location: String,
    priority: String,
    dueDate: String,
    isUrgent: Boolean,
    onAccept: () -> Unit
)
```

### RejectedTaskCard

Card component for displaying rejected task information.

```kotlin
@Composable
fun RejectedTaskCard(
    caseNumber: String,
    location: String,
    priority: String,
    dueDate: String,
    reason: String,
    affectedPages: String,
    onRescan: () -> Unit = {}
)
```

### PendingReviewCard

Card component for displaying tasks pending review.

```kotlin
@Composable
fun PendingReviewCard(
    caseNumber: String,
    location: String,
    priority: String,
    dueDate: String,
    submittedDate: String,
    status: String
)
```

### ActionButton

Button component for quick actions on the Dashboard.

```kotlin
@Composable
fun ActionButton(
    title: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
)
```

## Alert Components

### ResolvedProblemCard

Card component for displaying resolved problems.

```kotlin
@Composable
fun ResolvedProblemCard(
    caseNumber: String,
    issueType: String,
    resolvedDate: String,
    resolution: String
)
```

### OverdueCard

Card component for displaying overdue tasks.

```kotlin
@Composable
fun OverdueCard(
    caseNumber: String,
    daysOverdue: Int
)
```

### RejectedCard

Card component for displaying rejected documents in the Alerts screen.

```kotlin
@Composable
fun RejectedCard(
    caseNumber: String,
    priority: String
)
```

### AlertSectionHeader

Header component for alert sections.

```kotlin
@Composable
fun AlertSectionHeader(
    icon: ImageVector,
    title: String,
    count: Int,
    iconColor: Color
)
```

## Scan Task Components

### ScanTaskItem

Item component for displaying scan tasks.

```kotlin
@Composable
fun ScanTaskItem(task: ScanTask)
```

### StatusCard

Card component for displaying status counts.

```kotlin
@Composable
fun StatusCard(
    title: String,
    count: Int,
    color: Color
)
```

### TaskToScanItem

Item component for displaying tasks to scan.

```kotlin
@Composable
fun TaskToScanItem(task: TaskToScan)
```

## Problem Reporting Components

### CategoryItem

Item component for displaying problem categories.

```kotlin
@Composable
fun CategoryItem(
    category: ProblemCategory,
    isSelected: Boolean,
    onClick: () -> Unit
)
```

## Design Principles

- **Consistency**: Components maintain consistent styling across the application
- **Reusability**: Components are designed to be reused in different contexts
- **Customizability**: Components accept parameters for customization
- **Accessibility**: Components consider accessibility with proper contrast and sizing

## Notes

- All components include preview functions for development purposes
- Components use Material 3 components under the hood
- Custom styling is applied through modifiers and parameters
