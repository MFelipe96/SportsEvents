package com.mypackage.sportsevents.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mypackage.sportsevents.data.local.dao.SportsDao
import com.mypackage.sportsevents.data.local.entity.EventEntity

@Database(entities = [EventEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun sportsDao(): SportsDao
}
