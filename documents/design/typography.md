# Typography System

## Overview

The SourceworxDMS application uses a consistent typography system throughout the UI. Typography is defined in the `Type.kt` file and used via the Material 3 theme.

## Text Styles

### Headings

- **Headline Large**
  - Used for main app title
  - Example: "SureScan" on the Login screen
  - Font weight: Bold
  - Font size: 32sp

- **Headline Medium**
  - Used for user name on Dashboard
  - Example: "Zanele" on the Dashboard screen
  - Font weight: Bold
  - Font size: 28sp

### Titles

- **Title Large**
  - Used for section headers
  - Example: "New Tasks" on the Dashboard screen
  - Font weight: Bold
  - Font size: 22sp

- **Title Medium**
  - Used for card titles and subsection headers
  - Example: Problem category title on Report Problem screen
  - Font weight: Bold
  - Font size: 18sp

### Body Text

- **Body Large**
  - Used for primary content text
  - Example: Welcome message on Dashboard screen
  - Font size: 16sp

- **Body Medium**
  - Used for secondary content text
  - Example: Task details on Scan Tasks screen
  - Font size: 14sp

- **Body Small**
  - Used for tertiary content and supporting text
  - Example: Task IDs and timestamps
  - Font size: 12sp

### Labels

- **Label Large**
  - Used for button text
  - Example: "Sign In" button text
  - Font weight: Medium
  - Font size: 16sp

- **Label Medium**
  - Used for status indicators and tags
  - Example: "Urgent" tag on task cards
  - Font weight: Medium
  - Font size: 14sp

- **Label Small**
  - Used for smallest UI elements
  - Example: Notification badge count
  - Font weight: Bold
  - Font size: 10sp

## Font Weights

- **Normal**: Regular text
- **Medium**: Emphasized text, buttons
- **Bold**: Headings, important information

## Implementation

Typography is defined in the `Type.kt` file:

```kotlin
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    // Other text styles...
)
```

And applied through the Material 3 theme in `Theme.kt`:

```kotlin
MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
)
```

## Custom Text Styling

In addition to the base typography, custom text styling is applied in composables:

```kotlin
Text(
    text = "Welcome back,",
    style = MaterialTheme.typography.bodyLarge.copy(
        color = Color.DarkGray,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp
    )
)
```

## Notes

- The typography system follows Material Design guidelines
- Text styles are consistent across screens
- Font sizes are responsive and use scalable pixel units (sp)
- Custom styling is applied through the `copy()` function to maintain consistency
