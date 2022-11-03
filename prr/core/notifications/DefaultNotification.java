package prr.core.notifications;

import prr.core.client.Client;
import prr.core.terminal.Terminal;

public class DefaultNotification extends Notification {
  public DefaultNotification(Terminal from, String type, Client receiver) {
    super(from, type, receiver);
  }

  @Override
  public void sendNotification() {
    _receiver.addDefaultNotification(this);
  }

  @Override
  public String toString() {
    return _type + "|" + _from.getId();
  }
}
