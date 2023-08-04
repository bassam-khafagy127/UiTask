package com.example.uitask.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Tasks Table")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val subjectString: String?,
    val descriptionString: String?,
    val definitionOfDoneString: String?,
    val voiceNoteUri: String?,
    val assignees: String?,
    val ccAssignees: String?,
    val startDate: String?,
    val endDate: String?,
    val expectedWorkingHorus: String?,
    val repeated: String?,
    val priority: String?,
    val toDo: String?,
    val attachment: String?,
    val selectProject: String?
)
