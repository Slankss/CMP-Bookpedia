package com.plcoding.bookpedia.book.data.database

import androidx.sqlite.SQLiteException
import com.plcoding.bookpedia.book.data.database.dao.BookDao
import com.plcoding.bookpedia.book.data.database.entity.BookEntity
import com.plcoding.bookpedia.book.data.database.entity.QueryEntity
import com.plcoding.bookpedia.core.domain.DataError
import com.plcoding.bookpedia.core.domain.Result
import com.plcoding.bookpedia.core.domain.getCurrentTime

class LocalBookDataSourceImp(
    private val bookDao: BookDao
): LocalBookDataSource {
    override suspend fun getBooks(idList: List<String>): Result<List<BookEntity>, DataError.Local> {
        return try {
            bookDao.getBooks(idList).let { books ->
                if(books.isEmpty()){
                    Result.Error(DataError.Local.NOT_FOUND)
                }
                Result.Success(books)
            }
        } catch (e: SQLiteException){
            Result.Error(DataError.Local.NOT_FOUND)
        }
    }

    override suspend fun upsertBook(bookList: List<BookEntity>) {
        try {
            bookDao.upsertBook(bookList)
        } catch (_: SQLiteException){ }
    }

    override suspend fun getQuery(query: String): QueryEntity? {
        return try {
            bookDao.getQuery(query)
        } catch (e: SQLiteException){
            null
        }
    }

    override suspend fun upsertQuery(query: String, results: List<String>) {
        try {
            bookDao.upsertQuery(
                QueryEntity(
                    id = 0,
                    searchQuery = query,
                    timestamp = getCurrentTime(),
                    results = results.map { it.substringAfterLast("/") }
                )
            )
        } catch (_: SQLiteException){}
    }
}