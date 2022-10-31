package prr.core.notifications;

import prr.core.terminal.Terminal;

public class Notifications {

    private final NotificationType _type;

    private final Terminal _terminal;

    public Notifications(NotificationType type, Terminal terminal) {
        _type = type;
        _terminal = terminal;
    }

    public NotificationType getType() {
        return _type;
    }

    public String toString() {
        return _type + "|" + _terminal.getId();
    }
}
