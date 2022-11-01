package prr.core.notifications;

import prr.core.terminal.Terminal;

public class DefaultNotification extends Notification {
  public DefaultNotification(NotificationType type, Terminal terminal) {
    super(type, terminal);
  }

  public String toString() {
    return getType() + "|" + getTerminalId();
  }
}
