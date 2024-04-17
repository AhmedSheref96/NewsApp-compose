package com.el3asas.data.locale.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.el3asas.data.locale.entities.ArticleEntity

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNews(vararg articleEntity: ArticleEntity)

    @Delete
    suspend fun deleteNews(vararg articleEntity: ArticleEntity)

    @Query("select * from articleentity")
    fun getSaved(): PagingSource<Int, ArticleEntity>

    @Query("select * from articleentity where isFav=1")
    fun getFavorites(): PagingSource<Int, ArticleEntity>

}