package com.plcoding.bookpedia.book.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.plcoding.bookpedia.book.data.database.dao.BookDao
import com.plcoding.bookpedia.book.data.database.dao.FavoriteBookDao
import com.plcoding.bookpedia.book.data.database.entity.BookEntity
import com.plcoding.bookpedia.book.data.database.entity.FavoriteBookEntity
import com.plcoding.bookpedia.book.data.database.entity.QueryEntity

@Database(
    entities = [FavoriteBookEntity::class, BookEntity::class, QueryEntity::class],
    version = 1,
)
@TypeConverters(
    StringListTypeConverter::class
)
@ConstructedBy(BookDatabaseConstructor::class)
abstract class BookDatabase: RoomDatabase() {
    abstract val favoriteBookDao: FavoriteBookDao
    abstract val bookDao: BookDao

    companion object {
        const val DB_NAME = "BookDatabase"
    }
}