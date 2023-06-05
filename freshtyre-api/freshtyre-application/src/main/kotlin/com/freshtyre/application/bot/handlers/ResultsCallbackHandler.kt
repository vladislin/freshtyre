package com.freshtyre.application.bot.handlers

import com.freshtyre.business.adapter.Cache
import com.freshtyre.business.adapter.KitRepositoryAdapter
import com.freshtyre.business.facade.KitFacade
import com.freshtyre.domain.BotSearchRequest
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender


@Component
class ResultsCallbackHandler(private val kitFacade: KitFacade): Handler {

    override fun filter(update: Update): Boolean {
        return update.hasCallbackQuery()
    }

    override fun handle(update: Update, sender: AbsSender) {
        val kit = kitFacade.fetchKit(update.callbackQuery.data.toLong())!!
        val messageText = """
            <i><strong>${kit.name} ${kit.model}</strong></i>
            
            <i>Розмір:</i> ${kit.width.value}/${kit.height.value}/${kit.radius.value}
            <i>Сезон:</i> ${kit.season.value}
            <i>Кількість:</i> None
            <i>Дата випуску:</i> None 
            
            <i>Ціна:</i>   <code>${kit.price}$</code>
            
        """.trimIndent()


//        sender.execute(SendMediaGroup.builder().chatId(update.callbackQuery.message.chatId).medias().build())
        sender.execute(
            SendMessage.builder()
                .chatId(update.callbackQuery.message.chatId)
                .text(messageText)
                .parseMode("HTML")
                .build()
        )

    }
}