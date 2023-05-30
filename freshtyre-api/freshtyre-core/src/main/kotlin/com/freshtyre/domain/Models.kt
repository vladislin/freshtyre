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

data class Tyre( val id: Long, val kit: Long, val price: Float, val dot: Int )

interface ValueProvider {
    fun takeValue(): String
}

enum class TyreWidth(val value: Int): ValueProvider {
    W_205(205), W_215(215), W_225(225), W_235(235), W_245(245), W_285(285);

    companion object {
        fun fromValue(value: Int) = TyreWidth.values().firstOrNull() { it.value == value }
    }

    override fun takeValue() = this.name.removePrefix("W_")
}

enum class TyreHeight(val value: Int): ValueProvider {
    H_45(45), H_55(55), H_60(60), H_65(65);

    companion object {
        fun fromValue(value: Int) = TyreHeight.values().firstOrNull() { it.value == value }
    }

    override fun takeValue() = this.name.removePrefix("H_")
}

enum class TyreDiameter(val value: Int): ValueProvider {
    R14(14),R15(15),R16(16),R17(17),R18(18),R19(19),R20(20);

    companion object {
        fun fromValue(value: Int) = TyreDiameter.values().firstOrNull() { it.value == value }
    }

    override fun takeValue() = this.name.removePrefix("R")
}

enum class Season(val value: String): ValueProvider {
    WINTER("Зима"), SUMMER("Літо"), ALL_SEASON("Все сезон");

    companion object {
        fun fromValue(value: String): Season? = Season.values().firstOrNull() { it.value == value }
    }

    override fun takeValue() = this.value
}

data class BotSearchRequest(
    val id: Long,
    val username: String,
    var position: Position,
    var season: Season? = null,
    var width: TyreWidth? = null,
    var height: TyreHeight? = null,
    var diameter: TyreDiameter? = null
)

enum class Position {
    INPUT_SEASON, INPUT_WIDTH, INPUT_HEIGHT, INPUT_DIAMETER, RESULTS;
}
