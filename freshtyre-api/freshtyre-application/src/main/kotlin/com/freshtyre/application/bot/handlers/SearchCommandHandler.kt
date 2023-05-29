package com.freshtyre.application.bot.handlers

import com.freshtyre.domain.Season
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import org.telegram.telegrambots.meta.bots.AbsSender


@Component
class SearchCommandHandler : Handler {

    override fun filter(update: Update): Boolean {
        return update.message.hasText() && update.message.text.startsWith("Підібрати")
    }

    override fun handle(update: Update, sender: AbsSender) {
        val chooseSeasonMessage = SendMessage.builder()
            .text("Оберіть сезон:")
            .chatId(update.message.chatId)
            .replyMarkup(getSeasonsMarkup())
            .build()
        sender.execute(chooseSeasonMessage)
    }

    private fun getSeasonsMarkup(): ReplyKeyboardMarkup {
        val seasons: List<KeyboardRow> = Season.values().map {
            KeyboardRow(
                listOf(KeyboardButton.builder().text("${it.translate} ${it.emoji}").build())
            )
        }
        return ReplyKeyboardMarkup.builder()
            .oneTimeKeyboard(true)
            .resizeKeyboard(true)
            .keyboard(seasons)
            .build()
    }
}