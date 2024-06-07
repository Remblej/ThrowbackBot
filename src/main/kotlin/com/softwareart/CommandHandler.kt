package com.softwareart

import dev.kord.core.entity.channel.TextChannel

class CommandHandler(
    private val throwbacker: Throwbacker,
    private val scheduler: Scheduler
) {
    fun handleCommand(command: Command, channel: TextChannel): BotMessage {
        return try {
            when (command) {
                Command.DO_THROWBACK_ONCE -> throwbacker.createThrowbackMessage(channel)
                Command.ENABLE_DAILY_THROWBACK -> scheduler.enableDailyThrowbacks(channel)
                Command.DISABLE_DAILY_THROWBACK -> scheduler.disableDailyThrowbacks(channel)
            }
        } catch (e: Exception) {
            BotMessage("Unexpected error: ${e.message}")
        }
    }
}