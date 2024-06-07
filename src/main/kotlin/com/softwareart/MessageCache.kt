package com.softwareart

import com.github.benmanes.caffeine.cache.Caffeine
import dev.kord.common.entity.Snowflake
import dev.kord.core.Kord
import dev.kord.core.entity.Message
import dev.kord.core.entity.channel.TextChannel
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import kotlin.time.Duration.Companion.days
import kotlin.time.toJavaDuration

class MessageCache(private val bot: Kord) {
    private val log = LoggerFactory.getLogger(javaClass)

    private val cache = Caffeine.newBuilder()
        .refreshAfterWrite(1.days.toJavaDuration())
        .build(::fetchMessages)

    operator fun get(channelId: Snowflake): List<Message> = cache[channelId]

    private fun fetchMessages(channelId: Snowflake): List<Message> = runBlocking {
        log.info("Fetching messages for channel $channelId")
        bot.getChannelOf<TextChannel>(channelId)
            ?.messages
            ?.toList()
            ?: emptyList()
    }
}