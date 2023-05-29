package com.freshtyre.application.bot.handlers

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import org.telegram.telegrambots.meta.bots.AbsSender


@Component
class StartCommandHandler : Handler {

    override fun filter(update: Update): Boolean {
        return update.message.text == "/start" && update.message.isUserMessage
    }

    override fun handle(update: Update, sender: AbsSender) {
        val text = """
            Привіт <i>${update.message.from.firstName}</i>,
            Я бот який допоможе знайти шини для тебе.
            Нажміть кнопку "Підібрати", щоб розпочати пошук.
        """.trimIndent()

        val sendMessage = SendMessage.builder()
            .text(text)
            .parseMode("HTML")
            .replyMarkup(
                ReplyKeyboardMarkup
                    .builder()
                    .keyboardRow(
                        KeyboardRow(
                            listOf(
                                KeyboardButton.builder()
                                    .text("Підібрати \uD83D\uDD0E")
                                    .build()
                            )
                        )
                    )
                    .oneTimeKeyboard(true)
                    .resizeKeyboard(true)
                    .build()
            )
            .chatId(update.message.chatId)
            .parseMode("HTML")
            .build()
        sender.execute(sendMessage)
    }
}