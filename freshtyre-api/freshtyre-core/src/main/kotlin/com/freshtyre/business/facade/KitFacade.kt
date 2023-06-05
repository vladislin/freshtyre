package com.freshtyre.business.facade

import com.freshtyre.business.adapter.KitRepositoryAdapter
import com.freshtyre.domain.BotSearchRequest
import com.freshtyre.domain.Kit
import com.freshtyre.domain.KitRequest

class KitFacade(private val kitRepositoryAdapter: KitRepositoryAdapter) {

    fun fetchKits(request: KitRequest): List<Kit> {
        return kitRepositoryAdapter.fetchKits(request)
    }

    fun fetchKit(id: Long): Kit {
        return kitRepositoryAdapter.fetchKit(id)!!
    }
}