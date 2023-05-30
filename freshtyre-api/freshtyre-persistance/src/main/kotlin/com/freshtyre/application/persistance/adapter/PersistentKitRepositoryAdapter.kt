package com.freshtyre.application.persistance.adapter

import com.freshtyre.application.persistance.repository.KitRepository
import com.freshtyre.application.persistance.toBusinessEntity
import com.freshtyre.business.adapter.KitRepositoryAdapter
import com.freshtyre.domain.*
import org.springframework.stereotype.Service


@Service
class PersistentKitRepositoryAdapter(private val kitRepository: KitRepository): KitRepositoryAdapter {
    override fun fetchKits(searchRequest: BotSearchRequest): List<Kit> {
        val kits = kitRepository.findBySeasonAndWidthAndHeightAndDiameter(
            searchRequest.season!!,
            searchRequest.width!!,
            searchRequest.height!!,
            searchRequest.diameter!!
        )
        return kits.map { it.toBusinessEntity() }
    }

    override fun fetchKit(id: Long): Kit? {
        return kitRepository.findById(id).get().toBusinessEntity()
    }

    override fun persistKit(kit: Kit): Kit {
        TODO("Not yet implemented")
    }
}