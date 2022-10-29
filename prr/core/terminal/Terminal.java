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

  private Client _owner;

  private Map<String, Terminal> _friends = new TreeMap<>();

  private List<Communication> _receivedCommunications = new ArrayList<>(); //mudar para array de inteiros correspodentes ao id?

  private List<Communication> _madeCommunications = new ArrayList<>(); //mudar para array de inteiros correspodentes ao id?

  private Communication _ongoingCommunication;

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

  public boolean turnOff() {
    if (_mode == TerminalMode.OFF)
      return false;
    _mode = TerminalMode.OFF;
    return true;
  }

  public boolean turnOn() {
    if (_mode == TerminalMode.IDLE)
      return false;
    _mode = TerminalMode.IDLE;
    return true;
  }

  public boolean setOnSilent() {
    if (_mode == TerminalMode.SILENCE)
      return false;
    _mode = TerminalMode.SILENCE;
    return true;
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

  public Communication makeSMS(Terminal receiver, String message, int id) throws TerminalOffException {
    Communication textComm = receiver.acceptSMS(id, this, message, _owner.getClientLevel());
    _madeCommunications.add(textComm);
    _debt += textComm.getCost();
    return textComm;
  }

  protected Communication acceptSMS(int id, Terminal origin, String msg, ClientLevel level) throws TerminalOffException {
    if (_mode == TerminalMode.OFF)
      throw new TerminalOffException(_id);
      //FIXME adicionar mandar/criar notificacao

    Communication textComm = new TextCommunication(id, origin, this, msg, level);
    _receivedCommunications.add(textComm);
    return textComm;
  }

  public Communication makeVoiceCall(Terminal receiver, int id) throws TerminalException {
    Communication voiceComm = receiver.acceptVoiceCall(id, this);
    _ongoingCommunication = voiceComm;
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
    _mode = TerminalMode.BUSY;
    _receivedCommunications.add(voiceComm);
    return voiceComm;
  }

  public abstract Communication makeVideoCall(Terminal receiver, int id);

  protected abstract Communication acceptVideoCall(Terminal origin);


  public void endOnGoingCommunication(int size) {

  }


  /**
   * Checks if this terminal can end the current interactive communication.
   *
   * @return true if this terminal is busy (i.e., it has an active interactive communication) and
   *          it was the originator of this communication.
   **/
  public boolean canEndCurrentCommunication() {
    if (_mode == TerminalMode.BUSY /*FIXME verificar q é quem comecou a comm */)
      return true;
    return false;
  }
  
  /**
   * Checks if this terminal can start a new communication.
   *
   * @return true if this terminal is neither off neither busy, false otherwise.
   **/
  public boolean canStartCommunication() {
    if (_mode == TerminalMode.IDLE || _mode == TerminalMode.SILENCE)
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
