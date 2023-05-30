package com.freshtyre.application.bot.handlers

import com.freshtyre.application.bot.cache.Cache
import com.freshtyre.business.adapter.KitRepositoryAdapter
import com.freshtyre.domain.*
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import org.telegram.telegrambots.meta.bots.AbsSender


@Component
class SearchCommandHandler(
    private val cache: Cache<BotSearchRequest>,
    private val kitRepositoryAdapter: KitRepositoryAdapter
) : Handler {

//    override fun filter(update: Update): Boolean {
//        return update.message.hasText()
//    }

    override fun handle(update: Update, sender: AbsSender) {
        val searchRequest = cache.findById(update.message.chatId)
        if (update.message.text.startsWith("Підібрати") || update.message.text.equals("/find")) {
            val newSearch = createNewBotUserSearch(update)
            cache.add(newSearch)
            val chooseSeasonMessage = SendMessage.builder()
                .chatId(update.message.chatId)
                .text("Виберіть сезон")
                .replyMarkup(Season.values().toMarkup())
                .build()
            sender.execute(chooseSeasonMessage)
        }
        if (searchRequest != null) {
            when (searchRequest.position) {
                Position.INPUT_SEASON -> if (update.message.hasText()) {
                    searchRequest.season = Season.fromValue(update.message.text)
                    searchRequest.position = Position.INPUT_WIDTH
                    sender.execute(prepareWidthChooseMessage(update))
                }

                Position.INPUT_WIDTH -> if (update.message.hasText()) {
                    searchRequest.width = TyreWidth.fromValue(update.message.text.toInt())
                    searchRequest.position = Position.INPUT_HEIGHT
                    sender.execute(prepareHeightChooseMessage(update))
                }
                Position.INPUT_HEIGHT -> if (update.message.hasText()) {
                    searchRequest.height = TyreHeight.fromValue(update.message.text.toInt())
                    searchRequest.position = Position.INPUT_DIAMETER
                    sender.execute(prepareDiameterChooseMessage(update))
                }
                Position.INPUT_DIAMETER -> if (update.message.hasText()) {
                    searchRequest.diameter = TyreDiameter.fromValue(update.message.text.toInt())
                    searchRequest.position = Position.RESULTS
                    val messageText = """
                        Результати для вашого запиту:
                        Сезон - <code>${searchRequest.season!!.value}</code>
                        Розмір - <code>${searchRequest.width!!.value}-${searchRequest.height!!.value}-${searchRequest.diameter!!.value}</code>
                    """.trimIndent()
                    sender.execute(
                        SendMessage.builder()
                            .chatId(update.message.chatId)
                            .text(messageText)
                            .parseMode("HTML")
                            .replyMarkup(prepareResults(searchRequest))
                            .build()
                    )
                }
                Position.RESULTS -> if (update.message.hasText()) {
                    sender.execute(SendMessage.builder().text("result...").build())
                }
            }
        }
    }

    private fun prepareResults(searchRequest: BotSearchRequest): InlineKeyboardMarkup? {
        val results = kitRepositoryAdapter.fetchKits(searchRequest)
        val buttons = results.map {
            listOf(
                InlineKeyboardButton.builder()
                    .text("${it.name} ${it.model}    $${it.price}")
                    .callbackData(it.id.toString())
                    .build()
            )
        }
        return InlineKeyboardMarkup.builder()
            .keyboard(buttons)
            .build()
    }

    private fun prepareDiameterChooseMessage(update: Update): SendMessage {
        return SendMessage.builder()
            .chatId(update.message.chatId)
            .text("Виберіть діаметер")
            .replyMarkup(TyreDiameter.values().toMarkup())
            .build()
    }

    private fun prepareHeightChooseMessage(update: Update): SendMessage {
        return SendMessage.builder()
            .chatId(update.message.chatId)
            .text("Виберіть висоту")
            .replyMarkup(TyreHeight.values().toMarkup())
            .build()
    }

    private fun prepareWidthChooseMessage(update: Update): SendMessage {
        return SendMessage.builder()
            .chatId(update.message.chatId)
            .text("Виберіть ширину")
            .replyMarkup(TyreWidth.values().toMarkup())
            .build()
    }

    private fun createNewBotUserSearch(update: Update): BotSearchRequest {
        return BotSearchRequest(
            id = update.message.chatId,
            username = update.message.from.userName,
            position = Position.INPUT_SEASON
        )
    }
}

private fun <T : ValueProvider> Array<T>.toMarkup(): ReplyKeyboardMarkup {
    val buttons = this.map {
        KeyboardRow(
            listOf(KeyboardButton.builder().text(it.takeValue()).build())
        )
    }
    return ReplyKeyboardMarkup.builder()
        .oneTimeKeyboard(true)
        .resizeKeyboard(true)
        .keyboard(buttons)
        .build()
}