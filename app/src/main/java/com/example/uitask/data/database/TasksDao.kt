package com.example.uitask.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.uitask.data.local.Task

@Dao
interface TasksDao {
    @Insert
    suspend fun insertTask(task: Task): Long

    @Query("SELECT*FROM `Tasks Table` ORDER BY id")
    suspend fun getAllTasks(): List<Task>

    @Query("SELECT * FROM `tasks table` WHERE id = :taskId")
    fun getTaskById(taskId: Long): Task?

}