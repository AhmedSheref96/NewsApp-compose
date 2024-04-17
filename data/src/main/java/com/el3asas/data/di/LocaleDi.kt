package com.el3asas.data.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.el3asas.data.locale.RoomDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocaleDi {

    @Provides
    @Singleton
    fun provideRoomDB(@ApplicationContext context: Context): RoomDB =
        Room.databaseBuilder(context, RoomDB::class.java, "news-app")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideNewsDao(roomDB: RoomDB) = roomDB.newsDao()

}