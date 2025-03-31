package com.plcoding.bookpedia.book.data.repository

import androidx.sqlite.SQLiteException
import com.plcoding.bookpedia.book.data.database.LocalBookDataSourceImp
import com.plcoding.bookpedia.book.data.database.dao.FavoriteBookDao
import com.plcoding.bookpedia.book.data.mapper.toBook
import com.plcoding.bookpedia.book.data.mapper.toBookEntity
import com.plcoding.bookpedia.book.data.mapper.toFavoriteBookEntity
import com.plcoding.bookpedia.book.data.remote.RemoteBookDataSource
import com.plcoding.bookpedia.book.domain.Book
import com.plcoding.bookpedia.book.domain.BookRepository
import com.plcoding.bookpedia.core.domain.DataError
import com.plcoding.bookpedia.core.domain.EmptyResult
import com.plcoding.bookpedia.core.domain.Result
import com.plcoding.bookpedia.core.domain.map
import com.plcoding.bookpedia.core.domain.onError
import com.plcoding.bookpedia.core.domain.onSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultBookRepository(
    private val remoteBookDataSource: RemoteBookDataSource,
    private val favoriteBookDao: FavoriteBookDao,
    private val localBookDataSource: LocalBookDataSourceImp
): BookRepository {
    override suspend fun searchBooks(query: String): Result<List<Book>, DataError> {
        val cacheQuery = localBookDataSource.getQuery(query)

        if(cacheQuery != null){
            // Get local data
            return localBookDataSource
                .getBooks(cacheQuery.results)
                .map { bookEntities ->
                    bookEntities.map { it.toBook() }
                }
        } else {
            // Get remote data
            return remoteBookDataSource
                .searchBooks(query,20)
                .onSuccess { searchResponse ->
                    localBookDataSource.upsertQuery(query, searchResponse.results.map { it.id })
                    localBookDataSource.upsertBook(searchResponse.results.map { it.toBookEntity() })
                }
                .onError {

                }
                .map { dto ->
                    dto.results.map { it.toBook() }
                }
        }
    }

    override suspend fun getBookDescription(id: String): Result<String?, DataError> {
        val localResult = favoriteBookDao.getFavoriteBook(id)

        return if (localResult == null){
            return remoteBookDataSource
                .getBookDescription(id)
                .map { it.description }
        } else {
            Result.Success(localResult.description)
        }
    }

    override fun getFavoriteBooks(): Flow<List<Book>> {
        return favoriteBookDao
            .getFavoriteBooks()
            .map { bookEntities ->
                bookEntities.map {
                    it.toBook()
                }
            }
    }

    override fun isBookFavorite(id: String): Flow<Boolean> {
        return favoriteBookDao
            .getFavoriteBooks()
            .map { favoriteBookEntities ->
                favoriteBookEntities.any { it.id == id }
            }
    }

    override suspend fun markAsFavorite(book: Book): EmptyResult<DataError.Local> {
        return try {
            favoriteBookDao.upsertFavorite(book.toFavoriteBookEntity())
            Result.Success(Unit)
        } catch (e: SQLiteException){
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteFromFavorites(id: String) {
        favoriteBookDao.deleteFavoriteBook(id)
    }
}