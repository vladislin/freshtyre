package com.freshtyre.application.persistance.repository

import com.freshtyre.application.persistance.KitEntity
import org.springframework.data.jpa.repository.JpaRepository

interface KitRepository : JpaRepository<KitEntity, Long>