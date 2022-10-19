package prr.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import prr.core.exception.DuplicateTerminalException;
import prr.core.exception.UnknownTerminalException;

// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Abstract terminal.
 */
abstract public class Terminal implements Serializable /* FIXME maybe addd more interfaces */{

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202208091753L;
  
  // FIXME define attributes
  // FIXME define contructor(s)
  // FIXME define methods

  private String _id;

  private double _debt;

  private double _payments;

  private TerminalMode _mode;

  private Client _owner;

  private Map<String, Terminal> _friends = new TreeMap<>();

  private List<Communication> _communications = new ArrayList<>();

  public Terminal(String id, Client owner) throws DuplicateTerminalException{
    _id = id;
    _owner = owner;
    _mode = TerminalMode.IDLE;
  }

  public String getId() {
    return _id;
  }

  public Client getOwner() {
    return _owner;
  }

  public TerminalMode getMode() {
    return _mode;
  }

  public double getPayments() {
    return _payments;
  }

  public double getDebt() {
    return _debt;
  }

  public void turnOff() {
    _mode = TerminalMode.OFF;
  }

  public void turnOn() {
    _mode = TerminalMode.IDLE;
  }

  public void setOnSilent() {
    _mode = TerminalMode.SILENCE;
  }

  public void addTerminalFriend(Terminal friend) throws UnknownTerminalException {

    if (!this.equals(friend) && !_friends.containsKey(friend.getId()))
      _friends.put(friend.getId(), friend);
  }

  public void removeTerminalFriend(String unfriend) throws UnknownTerminalException {
    _friends.remove(unfriend);
  }

  public boolean isActiveTerminal() {
    if (_communications.size() == 0)
      return false;
    return true;
  }

  private String getFriends() {
    List<String> list = new ArrayList<>(_friends.keySet());
    String joined = String.join(", ", list);
    if (list.size() > 0)
      joined = "|"+joined;
    return joined;
  }

  public String toString() {
    return _id +"|"+ _owner.getKey() +"|"+ _mode +"|"
           + Math.round(_payments) +"|"+ Math.round(_debt)
           + getFriends();
  }
  
  public boolean equals(Terminal t) {
    return _id.equals(t.getId());
  }

  /**
   * Checks if this terminal can end the current interactive communication.
   *
   * @return true if this terminal is busy (i.e., it has an active interactive communication) and
   *          it was the originator of this communication.
   **/
  public boolean canEndCurrentCommunication() {
    if (_mode == TerminalMode.BUSY)
      return true;
    return false;
    // FIXME add implementation code
  }
  
  /**
   * Checks if this terminal can start a new communication.
   *
   * @return true if this terminal is neither off neither busy, false otherwise.
   **/
  public boolean canStartCommunication() {
    if (_mode == TerminalMode.IDLE)
      return true;
    return false;
    // FIXME add implementation code
  }

  public boolean canTurnOnTerminal() {
    return true;
  } //Adicionar cenas

  public boolean canTurnOffTerminal() {
    return true;
  } //Adicionar cenas
  
  
}
