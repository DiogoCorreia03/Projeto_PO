package prr.core.terminal.terminalMode;

import java.io.Serializable;

import prr.core.client.clientLevels.ClientLevel;
import prr.core.communication.Communication;
import prr.core.exception.TerminalException;
import prr.core.exception.TerminalOffException;
import prr.core.terminal.Terminal;

public interface TerminalMode extends Serializable {
  static final long serialVersionUID = 202208091753L;

  private Terminal _terminal;

  public TerminalMode(Terminal terminal) {
    this.
  }

  public Communication makeSMS(Terminal receiver, String message, int id) throws TerminalOffException;

  public Communication acceptSMS(int id, Terminal origin, String msg, ClientLevel level) throws TerminalOffException;

  public Communication makeVoiceCall(Terminal receiver, int id) throws TerminalException;

  public Communication acceptVoiceCall(int id, Terminal origin) throws TerminalException;

  public Communication makeVideoCall(Terminal receiver, int id) throws TerminalException;

  public Communication acceptVideoCall(int id, Terminal origin) throws TerminalException;

  public String toString();
    

  }
