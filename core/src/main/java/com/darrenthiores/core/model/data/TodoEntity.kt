package com.darrenthiores.core.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todoEntity")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var title: String,
    var description: String,
    var done: Boolean = false
)