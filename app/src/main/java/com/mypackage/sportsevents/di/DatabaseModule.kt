package com.mypackage.sportsevents.di

import android.content.Context
import androidx.room.Room
import com.mypackage.sportsevents.data.local.AppDatabase
import com.mypackage.sportsevents.data.local.dao.SportsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "sports_db"
        ).build()
    }

    @Provides
    fun provideSportsDao(database: AppDatabase): SportsDao = database.sportsDao()
}
