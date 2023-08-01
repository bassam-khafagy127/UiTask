package com.example.uitask.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Tasks Table")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val subjectString: String?,
    val subjectVoiceNoteUri: String?,
    val descriptionString: String?,
    val descriptionVoiceNoteUri: String?,
    val definitionOfDoneString: String?,
    val definitionOfDoneVoiceNoteUri: String?,
    val voiceNote: String?,
    val assignees: String?,
    val ccAssignees: String?,
    val startDate: String?,
    val endDate: String?,
    val expectedWorkingHorus: Int?,
    val repeated: Boolean?,
    val priority: String?,
    val toDo: String?,
    val attachment: String?,
    val selectProject: String?
)
