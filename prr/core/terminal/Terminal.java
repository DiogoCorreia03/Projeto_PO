package prr.core.terminal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import prr.core.client.Client;
import prr.core.client.clientLevels.ClientLevel;
import prr.core.communication.Communication;
import prr.core.communication.TextCommunication;
import prr.core.communication.VoiceCommunication;
import prr.core.exception.DuplicateTerminalException;
import prr.core.exception.TerminalBusyException;
import prr.core.exception.TerminalException;
import prr.core.exception.TerminalOffException;
import prr.core.exception.TerminalSilenceException;
import prr.core.exception.UnknownTerminalException;
import prr.core.terminal.terminalMode.BusyMode;
import prr.core.terminal.terminalMode.IdleMode;
import prr.core.terminal.terminalMode.OffMode;
import prr.core.terminal.terminalMode.SilenceMode;
import prr.core.terminal.terminalMode.TerminalMode;

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

  public Terminal(String id, Client owner) throws DuplicateTerminalException{
    _id = id;
    _owner = owner;
    _mode = IdleMode.getInstance();
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

  public void addDebt(double d) {
    _debt += d;
  }

  public void setPreviousMode() {
    _mode = _previous;
  }

  public boolean turnOff() {
    if (_mode == OffMode.getInstance())
      return false;
    _mode = OffMode.getInstance();
    return true;
  }

  public boolean turnOn() {
    if (_mode == IdleMode.getInstance())
      return false;
    _mode = IdleMode.getInstance();
    return true;
  }

  public boolean setOnSilent() {
    if (_mode == SilenceMode.getInstance())
      return false;
    _mode = SilenceMode.getInstance();
    return true;
  }

  protected void setBusy() {
    _mode = BusyMode.getInstance();
  }

  public void setOngoingCommunication(Communication comm) {
    _ongoingCommunication = comm;
  }

  public void addMadeCommunication(Communication comm) {
    _madeCommunications.add(comm);
  }

  public void addReceivedCommunication(Communication comm) {
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

  private String getFriends() {
    List<String> list = new ArrayList<>(_friends.keySet());
    String joined = String.join(", ", list);
    if (list.size() > 0)
      joined = "|"+joined;
    return joined;
  }
 
  //public abstract Communication makeSMS(Terminal receiver, String message, int id) throws TerminalOffException;
    

  protected Communication acceptSMS(int id, Terminal origin, String msg, ClientLevel level) throws TerminalOffException {
    if (_mode == TerminalMode.OFF)
      throw new TerminalOffException(_id);
      //FIXME adicionar mandar/criar notificacao

    Communication textComm = new TextCommunication(id, origin, this, msg, level);
    _receivedCommunications.add(textComm);
    return textComm;
  }

  /*

  public Communication makeVoiceCall(Terminal receiver, int id) throws TerminalException {
    Communication voiceComm = receiver.acceptVoiceCall(id, this);
    _ongoingCommunication = voiceComm;
    _previous = _mode;
    _mode = TerminalMode.BUSY;
    _madeCommunications.add(voiceComm);
    return voiceComm;
  }

   protected Communication acceptVoiceCall(int id, Terminal origin) throws TerminalException {
    if (_mode == TerminalMode.OFF)
      throw new TerminalOffException(_id);
    if (_mode == TerminalMode.BUSY)
      throw new TerminalBusyException(_id);
    if (_mode == TerminalMode.SILENCE)
      throw new TerminalSilenceException(_id);

    Communication voiceComm = new VoiceCommunication(id, origin, this);
    _ongoingCommunication = voiceComm;
    _previous = _mode;
    _mode = TerminalMode.BUSY;
    _receivedCommunications.add(voiceComm);
    return voiceComm;
  }

  public abstract Communication makeVideoCall(Terminal receiver, int id) throws TerminalException;

  protected abstract Communication acceptVideoCall(int id, Terminal origin) throws TerminalException;
   */

  public void endOnGoingCommunication(int size) {
    _debt += _ongoingCommunication.endCommunication(size, _owner.getClientLevel());
    _ongoingCommunication = null;
  }


  /**
   * Checks if this terminal can end the current interactive communication.
   *
   * @return true if this terminal is busy (i.e., it has an active interactive communication) and
   *          it was the originator of this communication.
   **/
  public boolean canEndCurrentCommunication() {
    if (_mode == BusyMode.getInstance() && _ongoingCommunication.isOrigin(_id))
      return true;
    return false;
  }
  
  /**
   * Checks if this terminal can start a new communication.
   *
   * @return true if this terminal is neither off neither busy, false otherwise.
   **/
  public boolean canStartCommunication() {
    if (_mode == IdleMode.getInstance() || _mode == SilenceMode.getInstance())
      return true;
    return false;
  }
  
  public boolean equals(Terminal t) {
    return _id.equals(t.getId());
  }

  public String toString() {
    return _id +"|"+ _owner.getKey() +"|"+ _mode +"|"
           + Math.round(_payments) +"|"+ Math.round(_debt)
           + getFriends();
  }
}
