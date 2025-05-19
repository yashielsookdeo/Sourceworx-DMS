# SourceworxDMS - Document Management System

## Overview

SourceworxDMS is a document management system built with Jetpack Compose for Android. The application allows users to manage, scan, and track documents in a healthcare environment.

## Features

- User authentication
- Dashboard with task overview
- Document scanning functionality
- Task management
- Alerts and notifications
- Problem reporting

## Screens

The application consists of the following screens:

1. [Login Screen](screens/login.md) - User authentication
2. [Dashboard Screen](screens/dashboard.md) - Main screen with task overview
3. [Alerts Screen](screens/alerts.md) - Notifications and alerts
4. [Report Problem Screen](screens/report-problem.md) - Interface for reporting issues
5. [Scan Tasks Screen](screens/scan-tasks.md) - View and manage scanning tasks
6. [Tasks to Scan Screen](screens/tasks-to-scan.md) - List of documents to be scanned

## Architecture

The application follows a modern Android architecture with:

- Jetpack Compose for UI
- Navigation Component for screen navigation
- Material 3 design system

## Technology Stack

- Kotlin
- Jetpack Compose
- Navigation Component
- Material 3
- Coil for image loading
- Kotlin Coroutines
- Kotlin Serialization

## Project Structure

- `app/src/main/java/com/skyner/sourceworxdms/`
  - `MainActivity.kt` - Entry point of the application
  - `navigation/` - Navigation components
  - `ui/` - UI components and screens
    - `components/` - Reusable UI components
    - `screens/` - Application screens
    - `theme/` - Theme definitions

## Design System

The application uses a consistent design system with:

- [Colors](design/colors.md) - Color palette
- [Typography](design/typography.md) - Text styles
- [Components](design/components.md) - Reusable UI components

## Getting Started

To run the application:

1. Clone the repository
2. Open the project in Android Studio
3. Build and run the application on an emulator or physical device
