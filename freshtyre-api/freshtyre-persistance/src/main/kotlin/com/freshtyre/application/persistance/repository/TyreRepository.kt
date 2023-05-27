package com.freshtyre.application.persistance.repository

import com.freshtyre.application.persistance.TyreEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TyreRepository : JpaRepository<TyreEntity, Long> {

    fun findByKitId(id: Long): List<TyreEntity>

}