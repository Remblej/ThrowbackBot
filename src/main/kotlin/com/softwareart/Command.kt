package com.softwareart

enum class Command(
    val chatInput: String,
    val description: String
) {
    DO_THROWBACK_ONCE("throwback", "Executes one throwback"),
    ENABLE_DAILY_THROWBACK("enable_daily_throwback", "Enables automatic throwback once a day"),
    DISABLE_DAILY_THROWBACK("disable_daily_throwback", "Disables automatic daily throwback");

    companion object {
        fun fromChatInputOrNull(textCommand: String) =
            Command.entries.firstOrNull { it.chatInput == textCommand }
    }
}