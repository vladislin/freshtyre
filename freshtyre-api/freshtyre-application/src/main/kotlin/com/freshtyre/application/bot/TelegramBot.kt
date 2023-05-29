package com.freshtyre.application.bot

import com.freshtyre.application.bot.handlers.Handler
import com.freshtyre.application.configuration.TelegramBotProperties
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update

@Component
class TelegramBot(
    private val props: TelegramBotProperties,
    private val handlers: List<Handler>
): TelegramLongPollingBot() {

    override fun getBotToken() = props.token
    override fun getBotUsername() = props.username

    override fun onUpdateReceived(update: Update) {
        for (handler in handlers) {
            if (handler.filter(update)) {
                handler.handle(update, this)
                break
            }
        }
    }
}