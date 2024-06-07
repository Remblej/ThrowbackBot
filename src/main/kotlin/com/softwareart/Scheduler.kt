package com.softwareart

import dev.kord.common.entity.Snowflake
import dev.kord.core.behavior.channel.createMessage
import dev.kord.core.entity.channel.TextChannel
import kotlinx.coroutines.runBlocking
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.time.Duration.Companion.days

class Scheduler(private val throwbacker: Throwbacker) {
    private val timerByChannelId: MutableMap<Snowflake, Timer?> = ConcurrentHashMap()

    fun enableDailyThrowbacks(ch: TextChannel): BotMessage {
        if (timerByChannelId[ch.id] != null) {
            return BotMessage("Daily throwback is already enabled for this channel")
        }

        val task: TimerTask = object : TimerTask() {
            override fun run() {
                runBlocking {
                    val msg = throwbacker.createThrowbackMessage(ch)
                    ch.createMessage {
                        content = msg.content
                        msg.attachments.forEach { addFile(it.filename, it.provider) }
                    }
                }
            }
        }
        val delay = 0L
        val period: Long = 1.days.inWholeMilliseconds
        timerByChannelId[ch.id] = Timer().apply {
            schedule(task, delay, period)
        }

        return BotMessage("Enabled daily throwback for this channel")
    }

    fun disableDailyThrowbacks(ch: TextChannel): BotMessage {
        timerByChannelId[ch.id]?.cancel()
        timerByChannelId -= ch.id
        return BotMessage("Disabled daily throwback for this channel")
    }
}