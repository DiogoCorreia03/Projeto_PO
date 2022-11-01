package prr.core.terminal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import prr.core.client.Client;
import prr.core.client.clientLevels.ClientLevel;
import prr.core.communication.Communication;
import prr.core.exception.DuplicateTerminalException;
import prr.core.exception.TerminalException;
import prr.core.exception.TerminalOffException;
import prr.core.exception.UnknownTerminalException;

/**
 * Abstract terminal.
 */
abstract public class Terminal implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202208091753L;

  private String _id;

  private double _debt;

  private double _payments;

  private TerminalMode _mode;

  private TerminalMode _previous;

  private Client _owner;

  private Map<String, Terminal> _friends = new TreeMap<>();

  private List<Communication> _receivedCommunications = new ArrayList<>();

  private List<Communication> _madeCommunications = new ArrayList<>();

  private Communication _ongoingCommunication;

  public Terminal(String id, Client owner) throws DuplicateTerminalException {
    _id = id;
    _owner = owner;
    _mode = new IdleMode(this);
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

  protected void addDebt(double d) {
    _debt += d;
  }

  public void setPreviousMode() { //FIXME try to be protected
    _mode = _previous;
  }

  protected void setPrevious(TerminalMode mode) {
    _previous = mode;
  }

  public boolean turnOff() {
    if (_mode instanceof OffMode)
      return false;
    _mode = new OffMode(this);
    return true;
  }

  public boolean turnOn() {
    if (_mode instanceof IdleMode)
      return false;
    _mode = new IdleMode(this);
    return true;
  }

  public boolean setOnSilent() {
    if (_mode instanceof SilenceMode)
      return false;
    _mode = new SilenceMode(this);
    return true;
  }

  protected void setBusy() {
    _mode = new BusyMode(this);
  }

  protected void setOngoingCommunication(Communication comm) {
    _ongoingCommunication = comm;
  }

  protected void addMadeCommunication(Communication comm) {
    _madeCommunications.add(comm);
  }

  protected void addReceivedCommunication(Communication comm) {
    _receivedCommunications.add(comm);
  }

  public void addTerminalFriend(Terminal friend) throws UnknownTerminalException {

    if (!this.equals(friend) && !_friends.containsKey(friend.getId()))
      _friends.put(friend.getId(), friend);
  }

  public void removeTerminalFriend(String unfriend) throws UnknownTerminalException {
    _friends.remove(unfriend);
  }

  public boolean isActiveTerminal() {
    if (_receivedCommunications.size() == 0 && _madeCommunications.size() == 0)
      return false;
    return true;
  }

  public Communication makeSMS(Terminal receiver, String message, int id) throws TerminalException {
    try {
      Communication textComm = _mode.makeSMS(receiver, message, id, _owner.getClientLevel());
      addMadeCommunication(textComm);
      addDebt(textComm.getCost());
      return textComm;
    }
    catch (TerminalException e) {
      throw e;
    }
  }

  protected Communication acceptSMS(Terminal origin, String msg, int id, ClientLevel level) throws TerminalException {
    try {
      Communication textComm = _mode.acceptSMS(origin, msg, id, level);
      addReceivedCommunication(textComm);
      return textComm;
    }
    catch (TerminalException e) {
      throw e;
    }
  }

  public Communication makeVoiceCall(Terminal receiver, int id) throws TerminalException {
    try {
      Communication voiceComm = _mode.makeVoiceCall(receiver, id);
      setOngoingCommunication(voiceComm);
      setPrevious(_mode);
      setBusy();
      addMadeCommunication(voiceComm);
      return voiceComm;
    }
    catch (TerminalException e) {
      throw e;
    }
  }

  protected Communication acceptVoiceCall(Terminal origin, int id) throws TerminalException {
    try {
      Communication voiceComm = _mode.acceptVoiceCall(origin, id);
      setOngoingCommunication(voiceComm);
      setPrevious(_mode);
      setBusy();
      addReceivedCommunication(voiceComm);
      return voiceComm;
    }
    catch (TerminalException e) {
      throw e;
    }
  }

  public abstract Communication makeVideoCall(Terminal receiver, int id) throws TerminalException;

  protected abstract Communication acceptVideoCall(Terminal origin, int id) throws TerminalException;

  public void endOnGoingCommunication(int size) {
    _debt += _ongoingCommunication.endCommunication(size, _owner.getClientLevel());
    _ongoingCommunication = null;
  }

  /**
   * Checks if this terminal can end the current interactive communication.
   *
   * @return true if this terminal is busy (i.e., it has an active interactive
   *         communication) and
   *         it was the originator of this communication.
   **/
  public boolean canEndCurrentCommunication() {
    if (_mode instanceof BusyMode && _ongoingCommunication.isOrigin(_id))
      return true;
    return false;
  }

  /**
   * Checks if this terminal can start a new communication.
   *
   * @return true if this terminal is neither off neither busy, false otherwise.
   **/
  public boolean canStartCommunication() {
    if (_mode instanceof IdleMode || _mode instanceof SilenceMode)
      return true;
    return false;
  }

  public boolean equals(Terminal t) {
    return _id.equals(t.getId());
  }

  private String getFriends() {
    List<String> list = new ArrayList<>(_friends.keySet());
    String joined = String.join(", ", list);
    if (list.size() > 0)
      joined = "|" + joined;
    return joined;
  }

  public String toString() {
    return _id + "|" + _owner.getKey() + "|" + _mode + "|"
        + Math.round(_payments) + "|" + Math.round(_debt)
        + getFriends();
  }
}
