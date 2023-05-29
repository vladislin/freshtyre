package com.freshtyre.business.adapter

import com.freshtyre.domain.Tyre

interface TyreRepositoryAdapter {
    fun fetchTires(): List<Tyre>
    fun fetchTyre(): Tyre?
    fun persistTyre(): Tyre
}