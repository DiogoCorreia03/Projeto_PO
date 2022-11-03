package prr.core.terminal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import prr.core.client.Client;
import prr.core.client.clientLevels.ClientLevel;
import prr.core.communication.Communication;
import prr.core.exception.DuplicateTerminalException;
import prr.core.exception.TerminalBusyException;
import prr.core.exception.NoOnGoingCommunicationException;
import prr.core.exception.TerminalOffException;
import prr.core.exception.TerminalSilenceException;
import prr.core.exception.UnknownCommunicationException;
import prr.core.exception.UnknownTerminalException;
import prr.core.exception.UnsupportedAtDestinationException;
import prr.core.exception.UnsupportedAtOriginException;
import prr.core.notifications.DefaultNotification;

/**
 * Abstract terminal.
 */
abstract public class Terminal implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202208091753L;

  private String _id;

  private double _debt;

  private double _payments;

  protected TerminalMode _mode;

  private TerminalMode _previous;

  private Client _owner;

  private SortedMap<String, Terminal> _friends = new TreeMap<>();

  private List<Communication> _receivedCommunications = new ArrayList<>();

  private List<Communication> _madeCommunications = new ArrayList<>();

  private Communication _ongoingCommunication;

  private Set<Client> _toNotify = new HashSet<>();

  public Terminal(String id, Client owner) throws DuplicateTerminalException {
    _id = id;
    _owner = owner;
    _mode = new IdleMode(this);
  }

  public String getId() {
    return _id;
  }

  public double getPayments() {
    return _payments;
  }

  public double getDebt() {
    return _debt;
  }

  protected Client getOwner() {
    return _owner;
  }

  protected void addDebt(double d) {
    _debt += d;
  }

  public void setPreviousMode() {
    TerminalMode temp = _mode;
    _mode = _previous;
    _previous = temp;
  }

  protected void setMode(TerminalMode mode) {
    _mode = mode;
  }

  protected void setPrevious(TerminalMode mode) {
    _previous = mode;
  }

  public List<Communication> getMadeCommunications() {
    List<Communication> temp = new ArrayList<>(_madeCommunications);
    return Collections.unmodifiableList(temp);
  }

  public List<Communication> getReceivedCommunications() {
    List<Communication> temp = new ArrayList<>(_receivedCommunications);
    return Collections.unmodifiableList(temp);
  }

  protected void addToNotify(Client c) {
    _toNotify.add(c);
  }

  public boolean turnOff() {
    return _mode.turnOff();
  }

  public boolean turnOn() {
    return _mode.turnOn();
  }

  public boolean setOnSilent() {
    return _mode.setOnSilent();
  }

  protected void setBusy() {
    _mode.setBusy();
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

  private Communication getMadeCommunication(int id) throws UnknownCommunicationException {
    for (Communication c : _madeCommunications)
      if (c.getId() == id)
        return c;
    throw new UnknownCommunicationException();
  }

  public void makePayment (int commID) throws UnknownCommunicationException {
    Communication comm = getMadeCommunication(commID);
    double cost = comm.getCost();
    _debt -= cost;
    _payments += cost;
    comm.Pay();
  }

  public Communication makeSMS(Terminal receiver, String message, int id) throws TerminalOffException {
      Communication textComm = _mode.makeSMS(receiver, message, id, _owner.getClientLevel());
      addMadeCommunication(textComm);
      addDebt(textComm.getCost());
      _owner.getClientLevel().changeLevel(_owner);
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
      setBusy();
      addMadeCommunication(voiceComm);
      return voiceComm;
  }

  protected Communication acceptVoiceCall(Terminal origin, int id) throws TerminalOffException, TerminalBusyException, TerminalSilenceException {
      Communication voiceComm = _mode.acceptVoiceCall(origin, id);
      setOngoingCommunication(voiceComm);
      setBusy();
      addReceivedCommunication(voiceComm);
      return voiceComm;
  }

  public abstract Communication makeVideoCall(Terminal receiver, int id) throws TerminalOffException, 
    TerminalBusyException, TerminalSilenceException, UnsupportedAtOriginException, UnsupportedAtDestinationException;

  protected abstract Communication acceptVideoCall(Terminal origin, int id) throws TerminalOffException, 
    TerminalBusyException, TerminalSilenceException, UnsupportedAtDestinationException;

  public double endOnGoingCommunication(int size) {
    double cost = _ongoingCommunication.endCommunication(size, _owner.getClientLevel());
    _debt += cost;
    _ongoingCommunication = null;
    _owner.getClientLevel().changeLevel(_owner);
    return cost;
  }

  public void sendNotifications() {
    for (Client c: _toNotify)
      if (c.getNotificationPreference())
        c.addNotification(new DefaultNotification(_previous.toString().toUpperCase().charAt(0)
          +"2"+_mode.toString().toUpperCase().charAt(0), this)); //FIXME tem de ficar mais bonito xD
    _toNotify.clear();
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
