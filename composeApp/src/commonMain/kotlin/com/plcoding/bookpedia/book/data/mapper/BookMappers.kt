package com.plcoding.bookpedia.book.data.mapper

import com.plcoding.bookpedia.book.data.database.entity.BookEntity
import com.plcoding.bookpedia.book.data.database.entity.FavoriteBookEntity
import com.plcoding.bookpedia.book.domain.Book

fun Book.toFavoriteBookEntity(): FavoriteBookEntity {
    return FavoriteBookEntity(
        id = id,
        title = title,
        description = description,
        imageUrl = imageUrl,
        languages = languages,
        authors = authors,
        firstPublishYear = firstPublishYear,
        ratingAverage = ratingAverage,
        ratingCount = ratingCount,
        numPagesMedian = numPages,
        numEditions = numEditions
    )
}

fun FavoriteBookEntity.toBook(): Book {
    return Book(
        id = id,
        title = title,
        description = description,
        imageUrl = imageUrl,
        languages = languages,
        authors = authors,
        firstPublishYear = firstPublishYear,
        ratingAverage = ratingAverage,
        ratingCount = ratingCount,
        numPages = numPagesMedian,
        numEditions = numEditions
    )
}

fun BookEntity.toBook(): Book {
    return Book(
        id = id,
        title = title,
        description = description,
        imageUrl = imageUrl,
        languages = languages,
        authors = authors,
        firstPublishYear = firstPublishYear,
        ratingAverage = ratingAverage,
        ratingCount = ratingCount,
        numPages = numPagesMedian,
        numEditions = numEditions
    )
}