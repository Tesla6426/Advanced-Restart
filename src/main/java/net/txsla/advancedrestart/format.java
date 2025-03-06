package net.txsla.advancedrestart;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class format {
    public static Component string (String string) {
        return MiniMessage.miniMessage().deserialize(
                // ampersand replacements will remain so that both text formatting methods work simultaneously
                // \& functionality will be added soon
                string.replaceAll("&", "§")
        );
    }
}
