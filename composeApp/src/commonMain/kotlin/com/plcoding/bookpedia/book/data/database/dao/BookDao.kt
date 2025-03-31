package com.plcoding.bookpedia.book.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.plcoding.bookpedia.book.data.database.entity.BookEntity
import com.plcoding.bookpedia.book.data.database.entity.QueryEntity

@Dao
interface BookDao {

    @Query("SELECT * FROM BookEntity WHERE id IN (:idList)")
    suspend fun getBooks(idList: List<String>): List<BookEntity>

    @Upsert
    suspend fun upsertBook(bookList: List<BookEntity>)

    @Query("SELECT * FROM QueryEntity WHERE searchQuery = :query LIMIT 1")
    suspend fun getQuery(query: String): QueryEntity?

    @Upsert
    suspend fun upsertQuery(query: QueryEntity)

    @Query("DELETE FROM QueryEntity WHERE id = :id")
    suspend fun deleteQuery(id: Int)

}