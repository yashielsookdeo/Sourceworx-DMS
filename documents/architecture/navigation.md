# Navigation System

## Overview

The SourceworxDMS application uses the Jetpack Navigation Component for managing navigation between screens. The navigation system is implemented using a NavGraph that defines the application's navigation structure.

## Navigation Components

### Screen Routes

The application defines screen routes in the `Screen.kt` file:

```kotlin
sealed class Screen(val route: String) {
    object Login : Screen("login_screen")
    object Dashboard : Screen("dashboard_screen")
    object Alerts : Screen("alerts_screen")
    object ScanTasks : Screen("scan_tasks_screen")
    object TasksToScan : Screen("tasks_to_scan_screen")
    object ReportProblem : Screen("report_problem_screen")
}
```

### Navigation Graph

The navigation graph is defined in the `NavGraph.kt` file and sets up the navigation structure:

```kotlin
@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        // Screen composables and navigation actions
    }
}
```

## Navigation Flow

### Login to Dashboard

- The application starts at the Login screen
- After successful login, the user is navigated to the Dashboard screen
- The Login screen is removed from the back stack

```kotlin
composable(route = Screen.Login.route) {
    LoginScreen(
        onLoginSuccess = {
            navController.navigate(Screen.Dashboard.route) {
                popUpTo(Screen.Login.route) { inclusive = true }
            }
        }
    )
}
```

### Dashboard to Other Screens

From the Dashboard, users can navigate to:

- Alerts screen
- Scan Tasks screen
- Tasks to Scan screen
- Report Problem screen

```kotlin
composable(route = Screen.Dashboard.route) {
    DashboardScreen(
        onNavigateToAlerts = { navController.navigate(Screen.Alerts.route) },
        onNavigateToScanTasks = { navController.navigate(Screen.ScanTasks.route) },
        onNavigateToTasksToScan = { navController.navigate(Screen.TasksToScan.route) },
        onNavigateToReportProblem = { navController.navigate(Screen.ReportProblem.route) }
    )
}
```

### Back Navigation

All screens except the Dashboard have a back button that navigates back to the previous screen:

```kotlin
composable(route = Screen.Alerts.route) {
    AlertsScreen(
        onBackClick = { navController.navigateUp() }
    )
}
```

## Implementation Details

The navigation system is implemented using:

- Jetpack Navigation Component
- Compose Navigation
- NavHost and NavController
- Navigation actions with popUpTo for managing the back stack

## Usage

The navigation system is initialized in the `SourceworxDMSApp` composable:

```kotlin
@Composable
fun SourceworxDMSApp() {
    val navController = rememberNavController()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        NavGraph(navController = navController)
    }
}
```

## Notes

- The navigation system uses a single activity architecture
- Each screen is a composable function
- Navigation actions are passed as lambda functions to screen composables
- The back stack is managed to ensure proper navigation flow
