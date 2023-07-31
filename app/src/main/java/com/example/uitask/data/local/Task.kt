package com.example.uitask.data.local

import com.example.uitask.data.local.task.DateRange
import com.example.uitask.data.local.task.DefinitionOfDone
import com.example.uitask.data.local.task.Description
import com.example.uitask.data.local.task.Subject

data class Task(
    val subject: Subject,
    val description: Description?,
    val definitionOfDone: DefinitionOfDone?,
    val voiceNote: String?,
    val assignees: List<String>?,
    val ccAssignees: List<String>?,
    val dateRange: DateRange,
    val expectedWorkingHorus: Int,
    val repeated: Boolean,
    val priority: String,
    val toDo: String?,
    val attachment: String?,
    val selectProject: String?
)
