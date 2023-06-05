package com.freshtyre.business.adapter

interface Cache<T> {
    fun findById(id: Long): T?
    fun removeById(id: Long)
    fun getAll(): List<T>
    fun add(t: T)
}