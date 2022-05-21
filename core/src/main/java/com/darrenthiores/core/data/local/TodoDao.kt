package com.darrenthiores.core.data.local

import androidx.room.*
import com.darrenthiores.core.model.data.TodoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Insert
    suspend fun insert(todo: TodoEntity)

    @Insert
    fun preInsert(vararg todo: TodoEntity)

    @Update
    suspend fun updateTodo(todo: TodoEntity)

    @Delete
    suspend fun deleteTodo(todo: TodoEntity)

    @Query("SELECT * FROM todoEntity")
    fun getTodos(): Flow<List<TodoEntity>>

    @Query("SELECT * FROM todoEntity WHERE done = 1")
    fun getCompletedTodos(): Flow<List<TodoEntity>>

    @Query("SELECT * FROM todoEntity WHERE done = 0")
    fun getUncompletedTodos(): Flow<List<TodoEntity>>

}