package com.freshtyre.application.persistance.repository

import com.freshtyre.application.persistance.KitEntity
import com.freshtyre.domain.Season
import com.freshtyre.domain.TyreDiameter
import com.freshtyre.domain.TyreHeight
import com.freshtyre.domain.TyreWidth
import org.springframework.data.jpa.repository.JpaRepository

interface KitRepository : JpaRepository<KitEntity, Long> {
    fun findBySeasonAndWidthAndHeightAndDiameter(season: Season, width: TyreWidth, height: TyreHeight, diameter: TyreDiameter): List<KitEntity>
}