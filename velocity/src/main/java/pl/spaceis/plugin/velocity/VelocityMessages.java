package pl.spaceis.plugin.velocity;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import pl.spaceis.plugin.config.Messages;

public class VelocityMessages extends Messages<Component> {

    private static final LegacyComponentSerializer LEGACY = LegacyComponentSerializer.builder().hexColors().build();

    @Override
    protected Component color(final String message) {
        return LEGACY.deserialize(message);
    }

    @Override
    public Component appendMessage(final Component message, final String append) {
        return message.append(Component.text(append));
    }

}
