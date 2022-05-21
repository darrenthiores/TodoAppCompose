package com.darrenthiores.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.darrenthiores.core.model.data.TodoEntity

@Database(entities = [TodoEntity::class], version = 1, exportSchema = false)
abstract class TodoDb: RoomDatabase() {

    abstract fun todoDao(): TodoDao

}