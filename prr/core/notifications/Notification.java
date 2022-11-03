package prr.core.notifications;

import java.io.Serializable;

import prr.core.client.Client;
import prr.core.terminal.Terminal;

public abstract class Notification implements Serializable {

    protected final Terminal _from;
  
    protected final String _type;

    protected final Client _receiver;
  
    public Notification(Terminal from, String type, Client receiver) {
      _from = from;
      _type = type;
      _receiver = receiver;
    }
  
    public abstract void sendNotification();
  }
