package prr.core.notifications;

import java.io.Serializable;

import prr.core.client.Client;
import prr.core.terminal.Terminal;

public abstract class NotificationFactory implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202208091753L;

  public Notification makeNotification(Terminal from, String type, Client receiver) {
    Notification notif = createNotification(from, type, receiver);
    notif.sendNotification();
    return notif;
  }

  public abstract Notification createNotification(Terminal from, String type, Client receiver);
}
