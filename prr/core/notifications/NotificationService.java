package prr.core.notifications;
import java.util.HashMap;
import java.util.Map;

import prr.core.client.Client;

public class NotificationService {
  private final Map<Client, Notification> _clients = new HashMap<>();

  public void add(Notification listener) {
    _clients.put(listener._receiver ,listener);
  }

  public void remove(Notification listener) {
    _clients.remove(listener._receiver);
  }

  private void removeAll() {
    _clients.clear();
  }

  public void notifyClients() {
    for (Notification n: _clients.values())
      n.sendNotification();
    removeAll();
  }
}
