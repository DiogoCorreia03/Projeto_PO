package prr.core.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import prr.core.client.clientLevels.*;
import prr.core.communication.CommsComparator;
import prr.core.communication.Communication;
import prr.core.exception.DuplicateClientException;
import prr.core.notifications.Notification;
import prr.core.terminal.Terminal;

public class Client implements Serializable{

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202208091753L;

  /** The client's key. */
  private String _key;

  /** The client's name. */
  private String _name;
  
  /** The client's tax number. */
  private int _nif;

  /** Client's notification prefernce. */
  private boolean _receiveNotifications;

  /** Client's level. */
  private ClientLevel _level;

  /** Client's terminals*/
  private Map<String, Terminal> _terminals = new TreeMap<>();

  /** Client's unread notifications */
  private List<Notification> _notifications = new ArrayList<>();

  public Client(String key, String name, int nif) throws DuplicateClientException {
    _key = key;
    _name = name;
    _nif = nif;
    _receiveNotifications = true;
    _level = NormalLevel.getInstance();
  }

  public String getKey() {
    return _key;
  }

  public String getName() {
    return _name;
  }

  public int getNif() {
    return _nif;
  }

  public boolean getNotificationPreference() {
    return _receiveNotifications;
  }

  public ClientLevel getClientLevel() {
    return _level;
  }

  public double getPayments() {
    double sum = 0;
    for (Terminal t: _terminals.values())
      sum += t.getPayments();

    return sum;
  }

  public double getDebts() {
    double sum = 0;
    for (Terminal t: _terminals.values())
      sum += t.getDebt();

    return sum;
  }

  public double getBalence() {
    return getPayments() - getDebts();
  }

  public void setLevel(ClientLevel level) {
    _level = level;
  }

  public List<Notification> showAllNotifications() {
    List<Notification> temp = new ArrayList<>(_notifications);
    _notifications.clear();
    return temp;
  }

  public void addTerminal(Terminal terminal) {
    _terminals.put(terminal.getId(), terminal);
  }

  public void addNotification(Notification n) {
    _notifications.add(n);
  }

  public boolean enableNotifications() {
    if (_receiveNotifications)
      return false;
    
    _receiveNotifications = true;
    return true;
  }

  public boolean disableNotifications() {
    if (!_receiveNotifications)
      return false;
    
    _receiveNotifications = false;
    return true;
  }

  public List<Communication> getReversedMadeCommunications() {
    List<Communication> temp = getMadeCommunications();
    Collections.sort(temp, new CommsComparator().reversed());
    return temp;
  }

  public List<Communication> getMadeCommunications() {
    List<Communication> temp = new ArrayList<>();
    for (Terminal t : _terminals.values())
      temp.addAll(t.getMadeCommunications());

    Collections.sort(temp, new CommsComparator());
    return temp;
  }

  public List<Communication> getReceivedCommunications() {
    List<Communication> temp = new ArrayList<>();
    for (Terminal t : _terminals.values())
      temp.addAll(t.getReceivedCommunications());

    Collections.sort(temp, new CommsComparator());
    return temp;
  }

  @Override
  public int hashCode() {
    return _key.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Client))
      return false;
    if (obj == this)
      return true;
    
    Client c = (Client) obj;
    return _key.equals(c.getKey());
  }

  @Override
  public String toString() {
    return "CLIENT|"+_key +"|"+ _name +"|"+ _nif +"|"+ _level
           +"|"+ (_receiveNotifications?"YES":"NO") +"|"+ _terminals.size() 
           +"|"+ Math.round(getPayments()) +"|"+ Math.round(getDebts());
  }
}
