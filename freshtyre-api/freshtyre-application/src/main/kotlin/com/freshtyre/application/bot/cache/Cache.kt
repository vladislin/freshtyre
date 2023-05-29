package com.freshtyre.application.bot.cache

interface Cache<T> {
    fun findById(id: Long): T
    fun removeById(id: Long)
    fun getAll(): List<T>
    fun add(t: T)
}