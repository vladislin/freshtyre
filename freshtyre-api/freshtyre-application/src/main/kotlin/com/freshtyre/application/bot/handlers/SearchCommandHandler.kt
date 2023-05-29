package com.freshtyre.application.bot.handlers

import com.freshtyre.application.bot.cache.Cache
import com.freshtyre.domain.*
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import org.telegram.telegrambots.meta.bots.AbsSender


@Component
class SearchCommandHandler(
    private val cache: Cache<BotSearchRequest>
) : Handler {

//    override fun filter(update: Update): Boolean {
//        return update.message.hasText()
//    }

    override fun handle(update: Update, sender: AbsSender) {
        val user = cache.findById(update.message.chatId)
        if (update.message.text.startsWith("Підібрати")) {
            val newSearch = createNewBotUserSearch(update)
            cache.add(newSearch)
            val chooseSeasonMessage = SendMessage.builder()
                .chatId(update.message.chatId)
                .text("Виберіть сезон")
                .replyMarkup(getSeasonsMarkup())
                .build()
            sender.execute(chooseSeasonMessage)
        }
        if (user != null) {
            when (user.position) {
                Position.INPUT_SEASON -> if (update.message.hasText()) {
                    user.season = Season.fromValue(update.message.text)
                    user.position = Position.INPUT_WIDTH
                    sender.execute(prepareWidthChooseMessage(update))
                }

                Position.INPUT_WIDTH -> if (update.message.hasText()) {
                    user.width = TyreWidth.fromValue(update.message.text.toInt())
                    user.position = Position.INPUT_HEIGHT
                    sender.execute(prepareHeightChooseMessage(update))
                }
                Position.INPUT_HEIGHT -> if (update.message.hasText()) {
                    user.height = TyreHeight.fromValue(update.message.text.toInt())
                    user.position = Position.INPUT_DIAMETER
                    sender.execute(prepareDiameterChooseMessage(update))
                }
                Position.INPUT_DIAMETER -> if (update.message.hasText()) {
                    user.diameter = TyreDiameter.fromValue(update.message.text.toInt())
                    user.position = Position.RESULTS
                    val messageText = """
                        Результати для вашого запиту:
                        Сезон - <code>${user.season!!.value}</code>
                        Розмір - <code>${user.width!!.value}-${user.height!!.value}-${user.diameter!!.value}</code>
                    """.trimIndent()
                    sender.execute(
                        SendMessage.builder()
                            .chatId(update.message.chatId)
                            .text(messageText)
                            .parseMode("HTML")
                            .build()
                    )
                    TODO("Fetch results from DB")
                }
                Position.RESULTS -> if (update.message.hasText()) {
                    sender.execute(SendMessage.builder().text("result...").build())
                }
            }
        }
    }

    private fun createNewBotUserSearch(update: Update): BotSearchRequest {
        return BotSearchRequest(
            id = update.message.chatId,
            username = update.message.from.userName,
            position = Position.INPUT_SEASON
        )
    }

    private fun prepareDiameterChooseMessage(update: Update): SendMessage {
        return SendMessage.builder()
            .chatId(update.message.chatId)
            .text("Виберіть діаметер")
            .replyMarkup(getDiameterMarkup())
            .build()
    }

    private fun prepareHeightChooseMessage(update: Update): SendMessage {
        return SendMessage.builder()
            .chatId(update.message.chatId)
            .text("Виберіть висоту")
            .replyMarkup(getHeightMarkup())
            .build()
    }

    private fun prepareWidthChooseMessage(update: Update): SendMessage {
        return SendMessage.builder()
            .chatId(update.message.chatId)
            .text("Виберіть ширину")
            .replyMarkup(getWidthMarkup())
            .build()
    }

    private fun getDiameterMarkup(): ReplyKeyboardMarkup {
        val values = TyreDiameter.values().map {
            KeyboardRow(
                listOf(KeyboardButton.builder().text(it.getValue()).build())
            )
        }
        return ReplyKeyboardMarkup.builder()
            .oneTimeKeyboard(true)
            .resizeKeyboard(true)
            .keyboard(values)
            .build()
    }

    private fun getHeightMarkup(): ReplyKeyboardMarkup {
        val values = TyreHeight.values().map {
            KeyboardRow(
                listOf(KeyboardButton.builder().text(it.getValue()).build())
            )
        }
        return ReplyKeyboardMarkup.builder()
            .oneTimeKeyboard(true)
            .resizeKeyboard(true)
            .keyboard(values)
            .build()
    }

    private fun getWidthMarkup(): ReplyKeyboardMarkup {
        val values = TyreWidth.values().map {
            KeyboardRow(
                listOf(KeyboardButton.builder().text(it.getValue()).build())
            )
        }
        return ReplyKeyboardMarkup.builder()
            .oneTimeKeyboard(true)
            .resizeKeyboard(true)
            .keyboard(values)
            .build()
    }

    private fun getSeasonsMarkup(): ReplyKeyboardMarkup {
        val seasons: List<KeyboardRow> = Season.values().map {
            KeyboardRow(
                listOf(KeyboardButton.builder().text(it.value).build())
            )
        }
        return ReplyKeyboardMarkup.builder()
            .oneTimeKeyboard(true)
            .resizeKeyboard(true)
            .keyboard(seasons)
            .build()
    }

}