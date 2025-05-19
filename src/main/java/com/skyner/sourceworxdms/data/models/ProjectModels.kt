package com.skyner.sourceworxdms.data.models

import kotlinx.serialization.Serializable

@Serializable
data class SeedData(
    val Projects: List<Project> = emptyList()
)

@Serializable
data class Project(
    val Name: String,
    val Description: String,
    val Icon: String,
    val Category: Category,
    val Tags: List<Tag>,
    val Tasks: List<ProjectTask>
)

@Serializable
data class Category(
    val Title: String,
    val Color: String
)

@Serializable
data class Tag(
    val Title: String,
    val Color: String
)

@Serializable
data class ProjectTask(
    val Title: String,
    val IsCompleted: Boolean
)
