package com.freshtyre.domain

data class Kit(
    val id: Long,
    val name: String,
    val model: String,
    val width: TyreWidth,
    val height: TyreHeight,
    val radius: TyreRadius,
    val price: Float,
    val season: Season,
)

data class Tyre( val id: Long, val kit: Long, val price: Float, val dot: Int )

interface ValueProvider {
    fun takeValue(): String
}

enum class TyreWidth(val value: Int): ValueProvider {
    W_205(205), W_215(215), W_225(225), W_235(235), W_245(245), W_285(285), NOT_SET(0);

    companion object {
        fun fromValue(value: Int) = TyreWidth.values().firstOrNull() { it.value == value }
    }

    override fun takeValue() = this.value.toString()
}

enum class TyreHeight(val value: Int): ValueProvider {
    H_45(45), H_55(55), H_60(60), H_65(65), NOT_SET(0);

    companion object {
        fun fromValue(value: Int) = TyreHeight.values().firstOrNull() { it.value == value }
    }

    override fun takeValue() = this.value.toString()
}

enum class TyreRadius(val value: Int): ValueProvider {
    R14(14),R15(15),R16(16),R17(17),R18(18),R19(19),R20(20), NOT_SET(0);

    companion object {
        fun fromValue(value: Int) = TyreRadius.values().firstOrNull() { it.value == value }
    }

    override fun takeValue() = this.value.toString()
}

enum class Season(val value: String): ValueProvider {
    WINTER("Зима"), SUMMER("Літо"), ALL_SEASON("Все сезон"), NOT_SET("Не вказано");

    companion object {
        fun fromValue(value: String): Season? = Season.values().firstOrNull() { it.value == value }
    }

    override fun takeValue() = this.value
}

data class BotSearchRequest(
    val id: Long,
    val username: String,
    var position: Position,
    var season: Season = Season.NOT_SET,
    var width: TyreWidth = TyreWidth.NOT_SET,
    var height: TyreHeight = TyreHeight.NOT_SET,
    var radius: TyreRadius = TyreRadius.NOT_SET
)

enum class Position {
    INPUT_SEASON, INPUT_WIDTH, INPUT_HEIGHT, INPUT_DIAMETER, NONE;
}

data class KitRequest(val season: Season, val width: TyreWidth, val height: TyreHeight, val radius: TyreRadius)
