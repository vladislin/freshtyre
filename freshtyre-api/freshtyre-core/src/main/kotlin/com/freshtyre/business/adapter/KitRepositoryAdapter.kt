package com.freshtyre.business.adapter

import com.freshtyre.domain.*

interface KitRepositoryAdapter {
    fun fetchKits(searchRequest: BotSearchRequest): List<Kit>
    fun fetchKit(id: Long): Kit?
    fun persistKit(kit: Kit): Kit

}