package com.example.uitask.di

import com.example.uitask.data.database.TaskedInAppDataBase
import com.example.uitask.repositories.TasksRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTasksRepository(
        taskedInAppDataBase: TaskedInAppDataBase
    ): TasksRepository {
        return TasksRepository(
            taskedInAppDataBase
        )
    }

}