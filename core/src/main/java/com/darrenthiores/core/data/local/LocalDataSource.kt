package com.darrenthiores.core.data.local

import com.darrenthiores.core.model.data.TodoEntity
import kotlinx.coroutines.flow.Flow

class LocalDataSource(
    private val todoDao: TodoDao
) {

    suspend fun insert(todo: TodoEntity) {
        todoDao.insert(todo)
    }

    suspend fun checklistTodo(todo: TodoEntity, state: Boolean) {
        todoDao.updateTodo(todo.copy(done = state))
    }

    suspend fun updateTodo(todo: TodoEntity, title: String, description: String) {
        todoDao.updateTodo(todo.copy(title = title, description = description))
    }

    suspend fun deleteTodo(todo: TodoEntity) {
        todoDao.deleteTodo(todo)
    }

    fun getTodos(): Flow<List<TodoEntity>> =
        todoDao.getTodos()

    fun getCompletedTodos(): Flow<List<TodoEntity>> =
        todoDao.getCompletedTodos()

    fun getUncompletedTodos(): Flow<List<TodoEntity>> =
        todoDao.getUncompletedTodos()

}