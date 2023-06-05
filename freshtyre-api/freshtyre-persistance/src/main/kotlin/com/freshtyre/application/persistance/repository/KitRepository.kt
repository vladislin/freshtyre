package com.freshtyre.application.persistance.repository

import com.freshtyre.application.persistance.KitEntity
import com.freshtyre.domain.Season
import com.freshtyre.domain.TyreRadius
import com.freshtyre.domain.TyreHeight
import com.freshtyre.domain.TyreWidth
import org.springframework.data.jpa.repository.JpaRepository

interface KitRepository : JpaRepository<KitEntity, Long> {
    fun findBySeasonAndWidthAndHeightAndRadius(season: Season, width: TyreWidth, height: TyreHeight, radius: TyreRadius): List<KitEntity>
}