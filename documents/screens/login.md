# Login Screen

## Overview

The Login Screen is the entry point of the SourceworxDMS application. It provides user authentication functionality with email and password fields, along with validation.

## UI Components

- **Logo**: SureScan logo displayed at the top
- **App Name**: "SureScan" title
- **Email Field**: Text field for entering email with validation
- **Password Field**: Secure text field for entering password
- **Sign In Button**: Button to submit credentials
- **Sign Up Link**: Text button to bypass authentication and navigate directly to the dashboard

## Features

### Authentication

- Email validation using regex pattern
- Password field with secure entry
- Error handling for invalid inputs
- Default email value for demonstration purposes

### Navigation

- Successful login navigates to the Dashboard Screen
- Sign Up button bypasses authentication and navigates directly to the Dashboard Screen

### Validation

- Email format validation
- Empty field validation
- Error messages displayed for invalid inputs

## Implementation Details

The Login Screen is implemented in `LoginScreen.kt` using Jetpack Compose. Key implementation details include:

- State management using `remember` and `mutableStateOf`
- Custom styled text fields with rounded corners
- Password field with visual transformation for secure entry
- Error handling with conditional display of error messages
- Navigation callback for successful authentication

## Design

- Clean, card-based design with drop shadow
- Consistent color scheme with the application theme
- Responsive layout that adapts to different screen sizes
- Accessibility considerations with proper content descriptions

## Usage

```kotlin
LoginScreen(
    onLoginSuccess = {
        // Handle navigation to Dashboard
    }
)
```

## Preview

The screen includes a preview function for development purposes:

```kotlin
@Preview(showBackground = true, showSystemUi = true, device = "spec:width=411dp,height=891dp")
@Composable
fun LoginScreenPreview() {
    SourceworxDMSTheme {
        LoginScreen(onLoginSuccess = {})
    }
}
```

## Notes

- For demonstration purposes, the Sign Up button bypasses authentication
- In a production environment, proper authentication would be implemented
- The default email is set to "admin@example.com" for testing
