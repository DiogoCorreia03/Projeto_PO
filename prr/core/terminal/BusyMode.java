package prr.core.terminal;

import prr.core.communication.Communication;
import prr.core.exception.TerminalBusyException;

public class BusyMode extends TerminalMode {

  public BusyMode(Terminal terminal) {
    super(terminal);
  }

  @Override
  public Communication acceptVoiceCall(Terminal origin, int id) throws TerminalBusyException {
    _terminal.addToNotify(origin.getOwner());
    throw new TerminalBusyException(_terminal.getId());
  }

  @Override
  public Communication acceptVideoCall(Terminal origin, int id) throws TerminalBusyException {
    _terminal.addToNotify(origin.getOwner());
    throw new TerminalBusyException(_terminal.getId());
  }

  @Override
  public boolean turnOff() {
    return false;
  }

  @Override
  public boolean turnOn() {
    _terminal.setPrevious(this);
    _terminal.setMode(new IdleMode(_terminal));
    return super.turnOn();
  }

  @Override
  public boolean setOnSilent() {
    _terminal.setPrevious(this);
    _terminal.setMode(new SilenceMode(_terminal));
    return true;
  }

  @Override
  public void setBusy() {}

  @Override
  public String toString() {
    return "BUSY";
  }

}
