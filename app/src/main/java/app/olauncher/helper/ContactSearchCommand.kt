package app.olauncher.helper

import androidx.annotation.StringRes
import app.olauncher.R

/** Commands activate after one space, but only while command mode is enabled. */
enum class ContactSearchCommand(
    val action: ContactAction?,
    @StringRes val hintRes: Int,
) {
    PHONE(ContactAction.CALL, R.string.contact_command_phone_hint),
    MESSAGE(ContactAction.TEXT, R.string.contact_command_message_hint),
    WHATSAPP(ContactAction.WHATSAPP, R.string.contact_command_whatsapp_hint),
    WEB(null, R.string.command_web_hint),
    AI(null, R.string.command_ai_hint);

    companion object {
        fun queryAfterPrefix(text: String): String = text.substringAfter(" ")

        fun parsePrefix(text: String, commandMode: Boolean = false): ContactSearchCommand? {
            if (!commandMode || !text.contains(" ")) return null
            return when (text.substringBefore(" ").lowercase(java.util.Locale.ROOT)) {
                "p" -> PHONE
                "m" -> MESSAGE
                "wa" -> WHATSAPP
                "w" -> WEB
                "ai" -> AI
                else -> null
            }
        }
    }
}
