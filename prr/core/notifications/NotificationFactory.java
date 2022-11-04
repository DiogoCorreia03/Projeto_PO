package prr.core.notifications;

import prr.core.client.Client;
import prr.core.terminal.Terminal;

public abstract class NotificationFactory {

  public Notification makeNotification(Terminal from, String type, Client receiver) {
    Notification notif = createNotification(from, type, receiver);
    notif.sendNotification();
    return notif;
  }

  public abstract Notification createNotification(Terminal from, String type, Client receiver);
}
