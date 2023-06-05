package com.freshtyre.business.adapter

import com.freshtyre.domain.*

interface KitRepositoryAdapter {
    fun fetchKits(request: KitRequest): List<Kit>
    fun fetchKit(id: Long): Kit?
    fun persistKit(kit: Kit): Kit

}