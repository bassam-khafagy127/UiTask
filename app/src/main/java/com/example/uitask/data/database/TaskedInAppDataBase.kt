package com.example.uitask.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.uitask.data.local.Task

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class TaskedInAppDataBase : RoomDatabase() {
    abstract fun tasksDao(): TasksDao
}