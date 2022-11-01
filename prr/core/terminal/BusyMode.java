package prr.core.terminal;

import prr.core.communication.Communication;
import prr.core.exception.TerminalBusyException;

public class BusyMode extends TerminalMode {

  public BusyMode(Terminal terminal) {
    super(terminal);
  }

  @Override
  public Communication acceptVoiceCall(Terminal origin, int id) throws TerminalBusyException {
    throw new TerminalBusyException(_terminal.getId());
  }

  @Override
  public Communication acceptVideoCall(Terminal origin, int id) throws TerminalBusyException {
    throw new TerminalBusyException(_terminal.getId());
  } 

  @Override
  public String toString() {
    return "BUSY";
  }
    
}
