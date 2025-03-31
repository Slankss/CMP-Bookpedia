package com.plcoding.bookpedia.book.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.plcoding.bookpedia.book.data.database.entity.FavoriteBookEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteBookDao {
    @Upsert
    suspend fun upsertFavorite(book: FavoriteBookEntity)

    @Query("SELECT * FROM FavoriteBookEntity")
    fun getFavoriteBooks(): Flow<List<FavoriteBookEntity>>

    @Query("SELECT * FROM FavoriteBookEntity WHERE id = :id")
    suspend fun getFavoriteBook(id:String): FavoriteBookEntity?

    @Query("DELETE FROM FavoriteBookEntity WHERE id = :id")
    suspend fun deleteFavoriteBook(id:String)
}