package com.softwareart

import dev.kord.core.Kord
import dev.kord.core.behavior.channel.asChannelOfOrNull
import dev.kord.core.behavior.interaction.respondPublic
import dev.kord.core.entity.channel.TextChannel
import dev.kord.core.event.interaction.ChatInputCommandInteractionCreateEvent
import dev.kord.core.on
import dev.kord.gateway.Intent
import dev.kord.gateway.Intents
import dev.kord.gateway.PrivilegedIntent
import org.slf4j.LoggerFactory
import java.lang.System.getenv

private val log = LoggerFactory.getLogger("Mainkt")

@OptIn(PrivilegedIntent::class)
suspend fun main() {
    val token = getenv("THROWBACK_BOT_TOKEN")
        ?: error("THROWBACK_BOT_TOKEN environment variable is not set")
    val bot = Kord(token)

    val messageCache = MessageCache(bot)
    val throwbacker = Throwbacker(messageCache)
    val scheduler = Scheduler(throwbacker)
    val commandHandler = CommandHandler(throwbacker, scheduler)

    Command.entries.forEach { bot.createGlobalChatInputCommand(it.chatInput, it.description) }

    bot.on<ChatInputCommandInteractionCreateEvent> {
        log.info(interaction.command.rootName)
        val channel = interaction.channel.asChannelOfOrNull<TextChannel>() ?: return@on
        val command = Command.fromChatInputOrNull(interaction.command.rootName) ?: return@on
        val botMessage = commandHandler.handleCommand(command, channel)
        interaction.respondPublic {
            content = botMessage.content
            botMessage.attachments.forEach { addFile(it.filename, it.provider) }
        }
    }

    bot.login {
        intents = Intents {
            +Intent.GuildMessages  // Listen for message events in guilds
            +Intent.MessageContent // Read the content of the messages
        }
    }
}
