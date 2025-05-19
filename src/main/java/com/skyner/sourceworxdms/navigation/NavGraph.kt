package com.skyner.sourceworxdms.navigation

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.skyner.sourceworxdms.ui.screens.alerts.AlertsScreen
import com.skyner.sourceworxdms.ui.screens.alerts.AlertsViewModel
import com.skyner.sourceworxdms.ui.screens.alerts.AlertsViewModelFactory
import com.skyner.sourceworxdms.ui.screens.dashboard.DashboardScreen
import com.skyner.sourceworxdms.ui.screens.dashboard.DashboardViewModel
import com.skyner.sourceworxdms.ui.screens.dashboard.DashboardViewModelFactory
import com.skyner.sourceworxdms.ui.screens.detail.TaskDetailScreen
import com.skyner.sourceworxdms.ui.screens.detail.TaskDetailViewModel
import com.skyner.sourceworxdms.ui.screens.detail.TaskDetailViewModelFactory
import com.skyner.sourceworxdms.ui.screens.login.LoginScreen
import com.skyner.sourceworxdms.ui.screens.problem.ReportProblemScreen
import com.skyner.sourceworxdms.ui.screens.scan.*

@Composable
fun NavGraph(navController: NavHostController) {
    // Get application context for ViewModels
    val context = LocalContext.current
    val application = context.applicationContext as Application

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(route = Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(route = Screen.Dashboard.route) {
            // Create ViewModel with application context
            val dashboardViewModel: DashboardViewModel = viewModel(
                factory = DashboardViewModelFactory(application)
            )

            DashboardScreen(
                onNavigateToAlerts = { navController.navigate(Screen.Alerts.route) },
                onNavigateToScanTasks = { navController.navigate(Screen.ScanTasks.route) },
                onNavigateToTasksToScan = { navController.navigate(Screen.TasksToScan.route) },
                onNavigateToReportProblem = { navController.navigate(Screen.ReportProblem.route) },
                onNavigateToTaskDetail = { projectId ->
                    navController.navigate(Screen.TaskDetail.createRoute(projectId))
                },
                viewModel = dashboardViewModel
            )
        }

        composable(route = Screen.Alerts.route) {
            // Create ViewModel with application context
            val alertsViewModel: AlertsViewModel = viewModel(
                factory = AlertsViewModelFactory(application)
            )

            AlertsScreen(
                onBackClick = { navController.navigateUp() },
                viewModel = alertsViewModel
            )
        }

        composable(route = Screen.ScanTasks.route) {
            // Create ViewModel with application context
            val scanTasksViewModel: ScanTasksViewModel = viewModel(
                factory = ScanTasksViewModelFactory(application)
            )

            ScanTasksScreen(
                onBackClick = { navController.navigateUp() },
                onNewScanClick = { navController.navigate(Screen.TasksToScan.route) },
                onProjectClick = { projectId -> navController.navigate(Screen.TaskDetail.createRoute(projectId)) },
                viewModel = scanTasksViewModel
            )
        }

        composable(route = Screen.TasksToScan.route) {
            // Create ViewModel with application context
            val tasksToScanViewModel: TasksToScanViewModel = viewModel(
                factory = TasksToScanViewModelFactory(application)
            )

            TasksToScanScreen(
                onBackClick = { navController.navigateUp() },
                onScanClick = { taskId, projectId ->
                    navController.navigate(Screen.DocumentScanner.createRoute(taskId, projectId))
                },
                viewModel = tasksToScanViewModel
            )
        }

        composable(route = Screen.ReportProblem.route) {
            ReportProblemScreen(
                onBackClick = { navController.navigateUp() }
            )
        }

        composable(
            route = Screen.TaskDetail.route,
            arguments = listOf(navArgument("projectId") { type = NavType.StringType })
        ) { backStackEntry ->
            // Get the project ID from the route arguments
            val projectId = backStackEntry.arguments?.getString("projectId") ?: ""

            // Create ViewModel with application context
            val taskDetailViewModel: TaskDetailViewModel = viewModel(
                factory = TaskDetailViewModelFactory(application)
            )

            TaskDetailScreen(
                projectId = projectId,
                onBackClick = { navController.navigateUp() },
                onScanClick = { taskId ->
                    navController.navigate(Screen.DocumentScanner.createRoute(taskId, projectId))
                },
                viewModel = taskDetailViewModel
            )
        }

        composable(
            route = Screen.DocumentScanner.route,
            arguments = listOf(
                navArgument("taskId") { type = NavType.StringType },
                navArgument("projectId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            // Get the task ID and project ID from the route arguments
            val taskId = backStackEntry.arguments?.getString("taskId") ?: ""
            val projectId = backStackEntry.arguments?.getString("projectId") ?: ""

            DocumentScannerScreen(
                taskId = taskId,
                projectId = projectId,
                onBackClick = { navController.navigateUp() },
                onScanComplete = {
                    // Navigate back to the ScanTasks screen and clear the back stack up to it
                    navController.navigate(Screen.ScanTasks.route) {
                        popUpTo(Screen.ScanTasks.route) { inclusive = false }
                    }
                }
            )
        }
    }
}
