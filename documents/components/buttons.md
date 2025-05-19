# Button Components

## Overview

The SourceworxDMS application uses a set of custom button components to maintain consistent styling throughout the application. These buttons are implemented as reusable composables.

## Button Types

### SureScanButton

A primary button used for main actions like signing in.

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

#### Features:
- Rounded corners (8dp)
- Custom colors from the theme
- Disabled state with reduced opacity
- Customizable padding
- Bold text

#### Usage:
```kotlin
SureScanButton(
    text = "Sign In",
    onClick = { /* Handle click */ },
    modifier = Modifier
        .fillMaxWidth()
        .height(50.dp)
)
```

### SourceworxButton

A standard button used for general actions throughout the application.

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

#### Features:
- Uses the primary color from the Material theme
- Consistent styling with the application theme
- Disabled state with reduced opacity
- Customizable padding

#### Usage:
```kotlin
SourceworxButton(
    text = "Primary Button",
    onClick = { /* Handle click */ },
    modifier = Modifier.fillMaxWidth()
)
```

### SourceworxSecondaryButton

A secondary button used for less prominent actions.

```kotlin
@Composable
fun SourceworxSecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = PaddingValues(vertical = 12.dp, horizontal = 16.dp)
)
```

#### Features:
- Uses the secondary color from the Material theme
- Consistent styling with the application theme
- Disabled state with reduced opacity
- Customizable padding

#### Usage:
```kotlin
SourceworxSecondaryButton(
    text = "Secondary Button",
    onClick = { /* Handle click */ },
    modifier = Modifier.fillMaxWidth()
)
```

## Implementation Details

The button components are implemented in:
- `SureScanButton.kt` - Primary sign-in button
- `Buttons.kt` - General purpose buttons

## Design

- Consistent styling with the application theme
- Clear visual hierarchy between primary and secondary actions
- Responsive to different screen sizes
- Accessibility considerations with proper text contrast

## Notes

- All buttons include preview functions for development purposes
- Buttons use Material 3 components under the hood
- Custom colors are defined in the theme
