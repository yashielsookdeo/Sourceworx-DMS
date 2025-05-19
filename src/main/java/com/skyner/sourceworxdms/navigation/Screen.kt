package com.skyner.sourceworxdms.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login_screen")
    object Dashboard : Screen("dashboard_screen")
    object Alerts : Screen("alerts_screen")
    object ScanTasks : Screen("scan_tasks_screen")
    object TasksToScan : Screen("tasks_to_scan_screen")
    object ReportProblem : Screen("report_problem_screen")
    object TaskDetail : Screen("task_detail_screen/{projectId}") {
        fun createRoute(projectId: String): String {
            return "task_detail_screen/$projectId"
        }
    }
    object DocumentScanner : Screen("document_scanner_screen/{taskId}/{projectId}") {
        fun createRoute(taskId: String, projectId: String): String {
            return "document_scanner_screen/$taskId/$projectId"
        }
    }
}
