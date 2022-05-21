package com.darrenthiores.core.model.domain

data class TodoDomain(
    val id: Int = 0,
    val title: String,
    val description: String,
    val done: Boolean = false
)
