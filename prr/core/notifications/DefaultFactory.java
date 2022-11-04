package prr.core.notifications;

import prr.core.client.Client;
import prr.core.terminal.Terminal;

public class DefaultFactory extends NotificationFactory {

  @Override
  public Notification createNotification(Terminal from, String type, Client receiver) {
    return new DefaultNotification(from, type, receiver);
  }
}