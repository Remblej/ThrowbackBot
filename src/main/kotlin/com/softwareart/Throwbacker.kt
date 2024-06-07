package com.softwareart

import dev.kord.core.entity.Attachment
import dev.kord.core.entity.Message
import dev.kord.core.entity.channel.TextChannel
import io.ktor.client.request.forms.*
import io.ktor.utils.io.*
import java.io.ByteArrayOutputStream
import java.net.URI

class Throwbacker(private val messageCache: MessageCache) {

    fun createThrowbackMessage(ch: TextChannel): BotMessage {
        val randomMessage = messageCache[ch.id].random()
        return createThrowbackMessage(ch, randomMessage)
    }

    private fun createThrowbackMessage(ch: TextChannel, originalMsg: Message): BotMessage {
        val dateTime = "<t:${originalMsg.timestamp.epochSeconds}>"
        val author = "<@${originalMsg.author?.id}>"
        val messageLink = "https://discord.com/channels/${ch.guildId}/${originalMsg.channelId}/${originalMsg.id}"
        val originalContent = originalMsg.content

        val messageContent = """
        $messageLink
        $dateTime $author sent:
        $originalContent
        """.trimIndent()
        val attachments = downloadAttachments(originalMsg.attachments)

        return BotMessage(messageContent, attachments)
    }

    private fun downloadAttachments(attachments: Set<Attachment>): List<BotMessage.Attachment> {
        return attachments.map { downloadAttachment(it) }
    }

    private fun downloadAttachment(attachment: Attachment): BotMessage.Attachment {
        val url = URI(attachment.url).toURL()
        val connection = url.openConnection()
        connection.setRequestProperty("User-Agent", "Mozilla/5.0")
        val inputStream = connection.getInputStream()
        val outputStream = ByteArrayOutputStream(inputStream.available())
        inputStream.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }
        val chProvider = ChannelProvider(outputStream.size().toLong()) {
            ByteReadChannel(outputStream.toByteArray())
        }
        return BotMessage.Attachment(attachment.filename, chProvider)
    }
}