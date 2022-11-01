package prr.core.notifications;

import prr.core.terminal.Terminal;

public abstract class Notification {

    private final NotificationType _type;

    private final Terminal _terminal;

    public Notification(NotificationType type, Terminal terminal) {
        _type = type;
        _terminal = terminal;
    }

    public NotificationType getType() {
        return _type;
    }

    public String getTerminalId() {
        return _terminal.getId();
    }
}
