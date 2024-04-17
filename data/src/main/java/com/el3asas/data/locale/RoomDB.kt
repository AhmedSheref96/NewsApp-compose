package com.el3asas.data.locale

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.el3asas.data.locale.daos.NewsDao
import com.el3asas.data.locale.entities.ArticleEntity

@TypeConverters(Converters::class)
@Database(entities = [ArticleEntity::class], exportSchema = false, version = 2)
abstract class RoomDB : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}