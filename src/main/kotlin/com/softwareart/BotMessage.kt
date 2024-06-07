package com.softwareart

import io.ktor.client.request.forms.*

data class BotMessage(
    val content: String,
    val attachments: List<Attachment> = emptyList()
) {
    data class Attachment(
        val filename: String,
        val provider: ChannelProvider
    )
}