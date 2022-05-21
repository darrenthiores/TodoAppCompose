package com.darrenthiores.todoappcompose.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darrenthiores.core.domain.TodoUseCase
import com.darrenthiores.core.model.presenter.Todo
import com.darrenthiores.core.utils.DataMapper
import com.darrenthiores.todoappcompose.screen.FilterState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(
    private val todoUseCase: TodoUseCase
): ViewModel() {

    private var _todos = MutableStateFlow<List<Todo>>(emptyList())
    val todos: StateFlow<List<Todo>>
        get() = _todos

    init {
        viewModelScope.launch {
            todoUseCase.getTodos().collect { todos ->
                _todos.value = if(todos.isNotEmpty()) {
                    DataMapper.mapDomainToPresenter(todos)
                } else {
                    emptyList()
                }
            }
        }
    }

    fun checklistTodo(todo: Todo, state: Boolean) {
        viewModelScope.launch {
            todoUseCase.checklistTodo(DataMapper.mapPresenterToDomain(todo), state)
        }
    }

    fun deleteTodo(todo: Todo) {
        viewModelScope.launch {
            todoUseCase.deleteTodo(DataMapper.mapPresenterToDomain(todo))
        }
    }

    fun filterTodo(filter: FilterState) {
        when(filter) {
            FilterState.Default -> {
                viewModelScope.launch {
                    todoUseCase.getTodos().collect { todos ->
                        _todos.value = if(todos.isNotEmpty()) {
                            DataMapper.mapDomainToPresenter(todos)
                        } else {
                            emptyList()
                        }
                    }
                }
            }
            FilterState.Complete -> {
                viewModelScope.launch {
                    todoUseCase.getCompletedTodos().collect { todos ->
                        _todos.value = if(todos.isNotEmpty()) {
                            DataMapper.mapDomainToPresenter(todos)
                        } else {
                            emptyList()
                        }
                    }
                }
            }
            FilterState.UnComplete -> {
                viewModelScope.launch {
                    todoUseCase.getUncompletedTodos().collect { todos ->
                        _todos.value = if(todos.isNotEmpty()) {
                            DataMapper.mapDomainToPresenter(todos)
                        } else {
                            emptyList()
                        }
                    }
                }
            }
        }
    }

}