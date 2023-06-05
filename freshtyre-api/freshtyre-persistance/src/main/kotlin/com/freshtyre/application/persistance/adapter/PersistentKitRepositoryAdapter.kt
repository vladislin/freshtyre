package com.freshtyre.application.persistance.adapter

import com.freshtyre.application.persistance.repository.KitRepository
import com.freshtyre.application.persistance.toBusinessEntity
import com.freshtyre.business.adapter.KitRepositoryAdapter
import com.freshtyre.domain.*
import org.springframework.stereotype.Service


@Service
class PersistentKitRepositoryAdapter(private val kitRepository: KitRepository): KitRepositoryAdapter {
    override fun fetchKits(request: KitRequest): List<Kit> {
        val kits = kitRepository.findBySeasonAndWidthAndHeightAndRadius(request.season, request.width, request.height, request.radius)
        return kits.map { it.toBusinessEntity() }
    }

    override fun fetchKit(id: Long): Kit? {
        return kitRepository.findById(id).get().toBusinessEntity()
    }

    override fun persistKit(kit: Kit): Kit {
        TODO("Not yet implemented")
    }
}