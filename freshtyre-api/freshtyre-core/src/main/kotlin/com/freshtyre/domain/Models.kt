package com.freshtyre.domain

data class Kit(
    val id: Long,
    val name: String,
    val model: String,
    val width: TyreWidth,
    val height: TyreHeight,
    val diameter: TyreDiameter,
    val price: Float,
    val season: Season,
)

data class Tyre(
    val id: Long,
    val kit: Long,
    val price: Float,
    val dot: Int
)

enum class TyreWidth {
    W_205,
    W_215,
    W_225,
    W_235,
    W_245,
    W_285
    ;

    fun getValue() = this.name.removePrefix("W_")
}

enum class TyreHeight {
    H_45,
    H_55,
    H_60,
    H_65
    ;

    fun getValue() = this.name.removePrefix("H_")
}

enum class TyreDiameter {
    R14,
    R15,
    R16,
    R17,
    R18,
    R19,
    R20
    ;

    fun getValue() = this.name.removePrefix("R")
}

enum class Season {
    WINTER,
    SUMMER
}