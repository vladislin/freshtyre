package com.freshtyre.application.bot.handlers

import com.freshtyre.business.adapter.Cache
import com.freshtyre.business.adapter.KitRepositoryAdapter
import com.freshtyre.business.facade.KitFacade
import com.freshtyre.domain.*
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import org.telegram.telegrambots.meta.bots.AbsSender


@Component
class SearchCommandHandler(
    private val cache: Cache<BotSearchRequest>,
    private val kitFacade: KitFacade
) : Handler {

    override fun filter(update: Update): Boolean {
        return update.hasMessage() &&
               update.message.text.startsWith("Підібрати") ||
               update.message.text.equals("/find") ||
               cache.findById(update.message.chatId) != null
    }

    override fun handle(update: Update, sender: AbsSender) {
        val searchRequest = cache.findById(update.message.chatId)

        if (searchRequest != null) {
            when (searchRequest.position) {
                Position.INPUT_SEASON -> if (update.message.hasText()) {
                    searchRequest.season = Season.fromValue(update.message.text)!!
                    searchRequest.position = Position.INPUT_WIDTH
                    sender.execute(prepareWidthChooseMessage(update))
                }

                Position.INPUT_WIDTH -> if (update.message.hasText()) {
                    searchRequest.width = TyreWidth.fromValue(update.message.text.toInt())!!
                    searchRequest.position = Position.INPUT_HEIGHT
                    sender.execute(prepareHeightChooseMessage(update))
                }
                Position.INPUT_HEIGHT -> if (update.message.hasText()) {
                    searchRequest.height = TyreHeight.fromValue(update.message.text.toInt())!!
                    searchRequest.position = Position.INPUT_DIAMETER
                    sender.execute(prepareRadiusChooseMessage(update))
                }
                Position.INPUT_DIAMETER -> if (update.message.hasText()) {
                    searchRequest.radius = TyreRadius.fromValue(update.message.text.toInt())!!
                    searchRequest.position = Position.NONE
                    sender.execute(prepareResults(searchRequest, update))
                }
                Position.NONE ->if (update.message.hasText() && update.message.text.equals("/find")) {
                    cache.removeById(update.message.chatId)
                    cache.add(createNewBotUserSearch(update))
                    sender.execute(prepareSeasonChooseMessage(update))
                }
            }
        } else {
            val newSearch = createNewBotUserSearch(update)
            cache.add(newSearch)
            sender.execute(prepareSeasonChooseMessage(update))
        }
    }

    private fun prepareResults(searchRequest: BotSearchRequest, update: Update): SendMessage {
        val messageText = """
            Результати для вашого запиту:
            Сезон - <code>${searchRequest.season.value}</code>
            Розмір - <code>${searchRequest.width.value}-${searchRequest.height.value}-${searchRequest.radius.value}</code>
            """.trimIndent()
        val request = KitRequest(searchRequest.season, searchRequest.width, searchRequest.height, searchRequest.radius)
        val results = kitFacade.fetchKits(request)
        val buttons = results.map {
            listOf(InlineKeyboardButton.builder().text("${it.name} ${it.model}    $${it.price}").callbackData(it.id.toString()).build())
        }
        return SendMessage.builder()
            .chatId(update.message.chatId)
            .text(messageText)
            .parseMode("HTML")
            .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
            .build()
    }

    private fun prepareRadiusChooseMessage(update: Update): SendMessage {
        return SendMessage.builder()
            .chatId(update.message.chatId)
            .text("Виберіть радіус")
            .replyMarkup(TyreRadius.values().toMarkup())
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

    private fun prepareSeasonChooseMessage(update: Update): SendMessage? {
        return SendMessage.builder()
            .chatId(update.message.chatId)
            .text("Виберіть сезон")
            .replyMarkup(Season.values().toMarkup())
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