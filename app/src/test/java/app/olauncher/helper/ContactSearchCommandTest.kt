package app.olauncher.helper

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class ContactSearchCommandTest {
    @Test fun commandsActivateOnSingleSpaceOnlyInCommandMode() {
        for ((alias, command) in listOf(
            "p" to ContactSearchCommand.PHONE,
            "m" to ContactSearchCommand.MESSAGE,
            "wa" to ContactSearchCommand.WHATSAPP,
            "w" to ContactSearchCommand.WEB,
            "ai" to ContactSearchCommand.AI,
        )) {
            assertNull(ContactSearchCommand.parsePrefix(alias, true))
            assertEquals(command, ContactSearchCommand.parsePrefix("$alias ", true))
            assertEquals(command, ContactSearchCommand.parsePrefix("${alias.uppercase()} ", true))
            assertEquals(command, ContactSearchCommand.parsePrefix("$alias Jamie", true))
            assertEquals("Jamie", ContactSearchCommand.queryAfterPrefix("$alias Jamie"))
            assertEquals("", ContactSearchCommand.queryAfterPrefix("$alias "))
            assertNull(ContactSearchCommand.parsePrefix("$alias ", false))
            assertNull(ContactSearchCommand.parsePrefix("$alias  Jamie", false))
        }
    }

    @Test fun ordinaryQueriesAndLegacyPeriodPrefixesDoNotActivate() {
        for (text in listOf("", "Paul", "Mary Smith", "Will", " p ", "p\tJamie", "m\nJamie", "x Jamie", "p. ", ".p ")) {
            assertNull(text, ContactSearchCommand.parsePrefix(text, true))
        }
    }

    @Test fun commandsUseTheRequestedContactActions() {
        assertEquals(ContactAction.CALL, ContactSearchCommand.PHONE.action)
        assertEquals(ContactAction.TEXT, ContactSearchCommand.MESSAGE.action)
        assertEquals(ContactAction.WHATSAPP, ContactSearchCommand.WHATSAPP.action)
    }
}
