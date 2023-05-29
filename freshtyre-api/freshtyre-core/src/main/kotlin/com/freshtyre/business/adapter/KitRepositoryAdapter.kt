package com.freshtyre.business.adapter

import com.freshtyre.domain.Kit

interface KitRepositoryAdapter {
    fun fetchKits(): List<Kit>
    fun fetchKit(): Kit?
    fun persistKit(): Kit

}