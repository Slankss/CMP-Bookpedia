package com.plcoding.bookpedia.book.data.database

import com.plcoding.bookpedia.book.data.database.entity.BookEntity
import com.plcoding.bookpedia.book.data.database.entity.QueryEntity
import com.plcoding.bookpedia.core.domain.DataError
import com.plcoding.bookpedia.core.domain.Result

interface LocalBookDataSource {
    suspend fun getBooks(idList: List<String>): Result<List<BookEntity>, DataError.Local>
    suspend fun upsertBook(bookList: List<BookEntity>)
    suspend fun getQuery(query: String): QueryEntity?
    suspend fun upsertQuery(query: String, results: List<String>)
}