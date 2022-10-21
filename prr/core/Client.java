package prr.core;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

import prr.core.exception.DuplicateClientException;

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
 

  public Client(String key, String name, int nif) throws DuplicateClientException {
    _key = key;
    _name = name;
    _nif = nif;
    _receiveNotifications = true;
    _level = ClientLevel.NORMAL;
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

  public void setNotifications(boolean x) {
    _receiveNotifications = x;
  }

  public void addTerminal(Terminal terminal) {
    _terminals.put(terminal.getId(), terminal);
  }  

  public String toString() {
    
    return "CLIENT|"+_key +"|"+ _name +"|"+ _nif +"|"+ _level
           +"|"+ (_receiveNotifications?"YES":"NO") +"|"+ _terminals.size() 
           +"|"+ Math.round(getPayments()) +"|"+ Math.round(getDebts());
  }
}
