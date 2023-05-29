package com.freshtyre.application.bot.handlers

import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender

interface Handler {
    fun filter(update: Update): Boolean
    fun handle(update: Update, sender: AbsSender)
}