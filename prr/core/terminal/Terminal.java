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
import prr.core.exception.TerminalBusyException;
import prr.core.exception.NoOnGoingCommunicationException;
import prr.core.exception.TerminalOffException;
import prr.core.exception.TerminalSilenceException;
import prr.core.exception.UnknownTerminalException;
import prr.core.exception.UnsupportedAtDestinationException;
import prr.core.exception.UnsupportedAtOriginException;

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

  protected TerminalMode getMode() {
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

  public void setPreviousMode() {
    _mode = _previous;
  }

  protected void setPrevious(TerminalMode mode) {
    _previous = mode;
  }

  public List<Communication> getMadeCommunications() {
    List<Communication> temp = new ArrayList<>(_madeCommunications);
    return temp;
  }

  public List<Communication> getReceivedCommunications() {
    List<Communication> temp = new ArrayList<>(_receivedCommunications);
    return temp;
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

  public String showOngoingCommunication() throws NoOnGoingCommunicationException {
    if (_ongoingCommunication == null) {
      throw new NoOnGoingCommunicationException();
    }
    return _ongoingCommunication.toString();
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

  public boolean isFriend(String id) {
    return _friends.containsKey(id);
  }

  public boolean isActiveTerminal() {
    if (_receivedCommunications.size() == 0 && _madeCommunications.size() == 0)
      return false;
    return true;
  }

  public Communication makeSMS(Terminal receiver, String message, int id) throws TerminalOffException {
      Communication textComm = _mode.makeSMS(receiver, message, id, _owner.getClientLevel());
      addMadeCommunication(textComm);
      addDebt(textComm.getCost());
      return textComm;
  }

  protected Communication acceptSMS(Terminal origin, String msg, int id, ClientLevel level) throws TerminalOffException {
      Communication textComm = _mode.acceptSMS(origin, msg, id, level);
      addReceivedCommunication(textComm);
      return textComm;
  }

  public Communication makeVoiceCall(Terminal receiver, int id) throws TerminalOffException, TerminalBusyException, TerminalSilenceException {
      Communication voiceComm = _mode.makeVoiceCall(receiver, id);
      setOngoingCommunication(voiceComm);
      setPrevious(_mode);
      setBusy();
      addMadeCommunication(voiceComm);
      return voiceComm;
  }

  protected Communication acceptVoiceCall(Terminal origin, int id) throws TerminalOffException, TerminalBusyException, TerminalSilenceException {
      Communication voiceComm = _mode.acceptVoiceCall(origin, id);
      setOngoingCommunication(voiceComm);
      setPrevious(_mode);
      setBusy();
      addReceivedCommunication(voiceComm);
      return voiceComm;
  }

  public abstract Communication makeVideoCall(Terminal receiver, int id) throws TerminalOffException, TerminalBusyException, TerminalSilenceException, UnsupportedAtOriginException, UnsupportedAtDestinationException;

  protected abstract Communication acceptVideoCall(Terminal origin, int id) throws TerminalOffException, TerminalBusyException, TerminalSilenceException, UnsupportedAtDestinationException;

  public long endOnGoingCommunication(int size) {
    double cost = _ongoingCommunication.endCommunication(size, _owner.getClientLevel());
    _debt += cost;
    _ongoingCommunication = null;
    return Math.round(cost);
  }

  /**
   * Checks if this terminal can end the current interactive communication.
   *
   * @return true if this terminal is busy (i.e., it has an active interactive
   *         communication) and
   *         it was the originator of this communication.
   **/
  public boolean canEndCurrentCommunication() { //FIXME isntanceof mal?
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
