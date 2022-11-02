package prr.core.terminal;

import java.io.Serializable;

import prr.core.client.clientLevels.ClientLevel;
import prr.core.communication.Communication;
import prr.core.communication.TextCommunication;
import prr.core.communication.VideoCommunication;
import prr.core.communication.VoiceCommunication;
import prr.core.exception.TerminalBusyException;
import prr.core.exception.TerminalOffException;
import prr.core.exception.TerminalSilenceException;
import prr.core.exception.UnsupportedAtDestinationException;
import prr.core.exception.UnsupportedAtOriginException;

public abstract class TerminalMode implements Serializable {
  static final long serialVersionUID = 202208091753L;

  protected Terminal _terminal;

  public TerminalMode(Terminal terminal) {
    _terminal = terminal;
  }

  public Communication makeSMS(Terminal receiver, String message, int id, ClientLevel level) throws TerminalOffException {
    return receiver.acceptSMS(_terminal, message, id, level);
  }

  public Communication acceptSMS(Terminal origin, String msg, int id, ClientLevel level) throws TerminalOffException {
    return new TextCommunication(id, origin, _terminal, msg, level);
  }

  public Communication makeVoiceCall(Terminal receiver, int id) throws TerminalOffException, TerminalBusyException, TerminalSilenceException {
    return receiver.acceptVoiceCall(_terminal, id);
  }

  public Communication acceptVoiceCall(Terminal origin, int id) throws TerminalOffException, TerminalBusyException, TerminalSilenceException {
    return new VoiceCommunication(origin, _terminal, id);
  }

  public Communication makeVideoCall(Terminal receiver, int id) throws TerminalOffException, TerminalBusyException, TerminalSilenceException, UnsupportedAtOriginException, UnsupportedAtDestinationException {
    return receiver.acceptVideoCall(_terminal, id);
  }

  public Communication acceptVideoCall(Terminal origin, int id) throws TerminalOffException, TerminalBusyException, TerminalSilenceException, UnsupportedAtDestinationException {
    return new VideoCommunication(origin, _terminal, id);
  }
}
