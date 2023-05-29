package com.freshtyre.application.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
data class TelegramBotProperties(
    @Value("\${telegram.token}") val token: String,
    @Value("\${telegram.username}") val username: String
)
