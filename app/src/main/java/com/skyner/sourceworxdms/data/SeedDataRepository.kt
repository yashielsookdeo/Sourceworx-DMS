package com.skyner.sourceworxdms.data

import android.content.Context
import android.util.Log
import com.skyner.sourceworxdms.data.models.Project
import com.skyner.sourceworxdms.data.models.SeedData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.io.FileNotFoundException
import java.io.IOException

/**
 * Repository for accessing seed data from assets
 */
class SeedDataRepository(private val context: Context) {

    private val json = Json { ignoreUnknownKeys = true }
    private var cachedData: SeedData? = null

    /**
     * Loads the seed data from assets folder
     */
    fun loadSeedData(): SeedData? {
        // Always use fallback data for now to ensure we have data
        return provideFallbackData()
    }

    /**
     * Provides fallback data when the JSON file cannot be loaded
     */
    private fun provideFallbackData(): SeedData? {
        return try {
            val defaultJson = """{
                "Projects": [
                  {
                    "Name": "CASE # 20250913/0045",
                    "Description": "Medical malpractice case involving surgical complications at City Hospital",
                    "Icon": "📋",
                    "Category": {
                      "Title": "Allocated",
                      "Color": "#3068DF"
                    },
                    "Tags": [
                      {
                        "Title": "Medical",
                        "Color": "#3068DF"
                      },
                      {
                        "Title": "High Priority",
                        "Color": "#FF4500"
                      }
                    ],
                    "Tasks": [
                      {
                        "Title": "Summons",
                        "IsCompleted": true
                      },
                      {
                        "Title": "Medical Records",
                        "IsCompleted": true
                      },
                      {
                        "Title": "Letter of Demands",
                        "IsCompleted": false
                      },
                      {
                        "Title": "Adverse Event",
                        "IsCompleted": false
                      },
                      {
                        "Title": "PAIA",
                        "IsCompleted": false
                      }
                    ]
                  },
                  {
                    "Name": "CASE # 20240901/7895",
                    "Description": "Personal injury case resulting from workplace accident at Construction Site",
                    "Icon": "🏥",
                    "Category": {
                      "Title": "Scanned",
                      "Color": "#8800FF"
                    },
                    "Tags": [
                      {
                        "Title": "Personal Injury",
                        "Color": "#FF4500"
                      },
                      {
                        "Title": "Workplace",
                        "Color": "#FFA500"
                      }
                    ],
                    "Tasks": [
                      {
                        "Title": "Summons",
                        "IsCompleted": true
                      },
                      {
                        "Title": "Medical Records",
                        "IsCompleted": true
                      },
                      {
                        "Title": "Letter of Demands",
                        "IsCompleted": true
                      },
                      {
                        "Title": "Adverse Event",
                        "IsCompleted": false
                      },
                      {
                        "Title": "PAIA",
                        "IsCompleted": false
                      }
                    ]
                  },
                  {
                    "Name": "CASE # 20260825/1234",
                    "Description": "Product liability case involving defective consumer electronics",
                    "Icon": "⚠️",
                    "Category": {
                      "Title": "Outstanding",
                      "Color": "#FF3300"
                    },
                    "Tags": [
                      {
                        "Title": "Product Liability",
                        "Color": "#B22222"
                      },
                      {
                        "Title": "Consumer",
                        "Color": "#FF8C00"
                      }
                    ],
                    "Tasks": [
                      {
                        "Title": "Product Specifications",
                        "IsCompleted": false
                      },
                      {
                        "Title": "Incident Reports",
                        "IsCompleted": false
                      },
                      {
                        "Title": "Expert Testing",
                        "IsCompleted": false
                      },
                      {
                        "Title": "Damage Assessment",
                        "IsCompleted": false
                      },
                      {
                        "Title": "Recall Information",
                        "IsCompleted": false
                      }
                    ]
                  }
                ]
            }""".trimIndent()
            val data = json.decodeFromString<SeedData>(defaultJson)
            cachedData = data
            data
        } catch (e: Exception) {
            Log.e("SeedDataRepository", "Error creating fallback data", e)
            null
        }
    }

    /**
     * Get all projects from seed data (suspending function for background execution)
     */
    suspend fun getProjectsAsync(): List<Project> = withContext(Dispatchers.IO) {
        loadSeedData()?.Projects ?: emptyList()
    }

    /**
     * Get all projects from seed data (synchronous version for backward compatibility)
     */
    fun getProjects(): List<Project> {
        return loadSeedData()?.Projects ?: emptyList()
    }

    /**
     * Get projects by category (suspending function for background execution)
     */
    suspend fun getProjectsByCategoryAsync(categoryTitle: String): List<Project> = withContext(Dispatchers.IO) {
        getProjectsAsync().filter { it.Category.Title == categoryTitle }
    }

    /**
     * Get projects by category (synchronous version for backward compatibility)
     */
    fun getProjectsByCategory(categoryTitle: String): List<Project> {
        return getProjects().filter { it.Category.Title == categoryTitle }
    }

    /**
     * Get allocated projects for dashboard (suspending function for background execution)
     */
    suspend fun getAllocatedProjectsAsync(): List<Project> = withContext(Dispatchers.IO) {
        getProjectsByCategoryAsync("Allocated")
    }

    /**
     * Get allocated projects (synchronous version for backward compatibility)
     */
    fun getAllocatedProjects(): List<Project> {
        return getProjectsByCategory("Allocated")
    }

    /**
     * Get outstanding projects for alerts (suspending function for background execution)
     */
    suspend fun getOutstandingProjectsAsync(): List<Project> = withContext(Dispatchers.IO) {
        getProjectsByCategoryAsync("Outstanding")
    }

    /**
     * Get outstanding projects (synchronous version for backward compatibility)
     */
    fun getOutstandingProjects(): List<Project> {
        return getProjectsByCategory("Outstanding")
    }

    /**
     * Get scanned projects for scan tasks (suspending function for background execution)
     */
    suspend fun getScannedProjectsAsync(): List<Project> = withContext(Dispatchers.IO) {
        getProjectsAsync().filter {
            it.Category.Title == "Scanned" || it.Category.Title == "Scanned Documents"
        }
    }

    /**
     * Get scanned projects (synchronous version for backward compatibility)
     */
    fun getScannedProjects(): List<Project> {
        return getProjects().filter {
            it.Category.Title == "Scanned" || it.Category.Title == "Scanned Documents"
        }
    }

    /**
     * Get a project by its ID (Name)
     */
    fun getProjectById(projectId: String): Project? {
        // Log the projectId and all available project names for debugging
        val projects = getProjects()
        Log.d("SeedDataRepository", "Looking for project with ID: $projectId")
        Log.d("SeedDataRepository", "Available projects: ${projects.map { it.Name }}")

        // Try to find an exact match first
        val exactMatch = projects.find { it.Name == projectId }
        if (exactMatch != null) {
            Log.d("SeedDataRepository", "Found exact match for project: ${exactMatch.Name}")
            return exactMatch
        }

        // If no exact match, try a contains match (case insensitive)
        val containsMatch = projects.find { it.Name.contains(projectId, ignoreCase = true) }
        if (containsMatch != null) {
            Log.d("SeedDataRepository", "Found contains match for project: ${containsMatch.Name}")
            return containsMatch
        }

        // If still no match, return the first project as a fallback (for demo purposes)
        if (projects.isNotEmpty()) {
            Log.d("SeedDataRepository", "No match found, returning first project as fallback: ${projects.first().Name}")
            return projects.first()
        }

        Log.d("SeedDataRepository", "No projects found at all")
        return null
    }

    /**
     * Async version of getProjectById
     */
    suspend fun getProjectByIdAsync(projectId: String): Project? = withContext(Dispatchers.IO) {
        getProjectById(projectId)
    }

    // Async methods are already defined above
}
