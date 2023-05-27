package com.freshtyre.application.persistance

import com.freshtyre.domain.*
import javax.persistence.*


fun KitEntity.toBusinessEntity(): Kit {
    return Kit(
        id = this.id,
        name = this.name,
        model = this.model,
        width = this.width,
        height = this.height,
        diameter = this.diameter,
        price = this.price,
        season = this.season
    )
}

fun Kit.toEntity(): KitEntity {
    return KitEntity(
        id = this.id,
        name = this.name,
        model = this.model,
        width = this.width,
        height = this.height,
        diameter = this.diameter,
        price = this.price,
        season = this.season
    )
}

fun TyreEntity.toBusinessEntity(): Tyre {
    return Tyre(
        id = this.id,
        kit = this.kit.id,
        price = this.price,
        dot = this.dot
    )
}

fun Tyre.toEntity(kit: KitEntity): TyreEntity {
    return TyreEntity(
        id = this.id,
        kit = kit,
        price = this.price,
        dot = this.dot
    )
}


@Table(name = "kit")
@Entity
class KitEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val name: String,
    val model: String,
    @Enumerated(EnumType.STRING)
    val width: TyreWidth,
    @Enumerated(EnumType.STRING)
    val height: TyreHeight,
    @Enumerated(EnumType.STRING)
    val diameter: TyreDiameter,
    @Enumerated(EnumType.STRING)
    val season: Season,
    val price: Float
)

@Table(name = "tyre")
@Entity
class TyreEntity(
    @Id
    val id: Long,
    @ManyToOne
    val kit: KitEntity,
    val price: Float,
    val dot: Int
)