package prr.core.notifications;

import prr.core.terminal.Terminal;

public class DefaultNotification extends Notification {
  public DefaultNotification(String type, Terminal terminal) {
    super(type, terminal);
  }

  @Override
  public String toString() {
    return getType() + "|" + getTerminalId();
  }
}
