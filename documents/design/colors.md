# Color System

## Overview

The SourceworxDMS application uses a consistent color system throughout the UI. Colors are defined in the `Color.kt` file and used via the Material 3 theme.

## Primary Colors

- **Primary Blue**: `Color(0xFF2196F3)`
  - Used for primary buttons, action buttons, and interactive elements
  - Example: Action buttons on the Dashboard screen

- **Sign In Button Color**: Custom color for the sign-in button
  - Example: Sign In button on the Login screen

## Status Colors

- **Success Green**: `Color(0xFF43A047)`
  - Used for success states, completed tasks, and resolved problems
  - Example: Completed status in Scan Tasks screen

- **Warning Amber**: `Color(0xFFFFA000)`
  - Used for warning states, medium priority, and pending tasks
  - Example: Medium priority tasks in Tasks to Scan screen

- **Error Red**: `Color(0xFFE53935)`
  - Used for error states, high priority, and rejected tasks
  - Example: Rejected tasks in Dashboard screen

## Background Colors

- **White**: `Color.White`
  - Primary background color for screens
  - Example: Background of most screens

- **Light Blue**: `Color(0xFFF5F8FF)`
  - Secondary background color for input fields
  - Example: Text fields in the Login screen

## Status Background Colors

- **Light Green**: `Color(0xFFE8F5E9)`
  - Background for success/resolved items
  - Example: Resolved problem cards in Alerts screen

- **Light Amber**: `Color(0xFFFFF8E1)`
  - Background for warning/pending items
  - Example: Overdue cards in Alerts screen

- **Light Red**: `Color(0xFFFFEBEE)`
  - Background for error/rejected items
  - Example: Rejected cards in Alerts screen

## Text Colors

- **Black**: `Color.Black`
  - Primary text color
  - Example: Headings and important text

- **Dark Gray**: `Color.DarkGray`
  - Secondary text color
  - Example: Subheadings and less important text

- **Gray**: `Color.Gray`
  - Tertiary text color
  - Example: Hints and disabled text

- **Sign Up Text Color**: Custom color for sign-up text
  - Example: Sign Up button text on the Login screen

## Usage in Theme

Colors are applied through the Material 3 theme in `Theme.kt`:

```kotlin
@Composable
fun SourceworxDMSTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
```

## Notes

- The application primarily uses a light theme
- Colors are used consistently to indicate status and priority
- Background colors have reduced opacity for subtle visual hierarchy
- The color system follows Material Design guidelines
