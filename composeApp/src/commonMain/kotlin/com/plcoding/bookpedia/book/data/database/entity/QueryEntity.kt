package com.plcoding.bookpedia.book.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class QueryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val searchQuery: String,
    val timestamp: Long,
    val results: List<String>
)
