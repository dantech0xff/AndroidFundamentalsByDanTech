package com.creative.androidfundamentalsbydantech.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "body") val body: String,
    @ColumnInfo(name = "created_at") val createdAt: Long = System.currentTimeMillis(),
    // Added in v2 migration; defaultValue keeps old rows valid.
    @ColumnInfo(name = "pinned", defaultValue = "0") val pinned: Boolean = false,
)
