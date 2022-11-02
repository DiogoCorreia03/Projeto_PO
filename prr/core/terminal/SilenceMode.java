package prr.core.terminal;

import prr.core.communication.Communication;
import prr.core.exception.TerminalSilenceException;

public class SilenceMode extends TerminalMode {

  public SilenceMode(Terminal terminal) {
    super(terminal);
  }

  @Override
  public Communication acceptVoiceCall(Terminal origin, int id) throws TerminalSilenceException {
    _terminal.addToNotify(origin.getOwner());
    throw new TerminalSilenceException(_terminal.getId());
  }

  @Override
  public Communication acceptVideoCall(Terminal origin, int id) throws TerminalSilenceException {
    _terminal.addToNotify(origin.getOwner());
    throw new TerminalSilenceException(_terminal.getId());
  }

  @Override
  public boolean turnOff() {
    _terminal.setPrevious(this);
    _terminal.setMode(new OffMode(_terminal));
    return true;
  }

  @Override
  public boolean turnOn() {
    _terminal.setPrevious(this);
    _terminal.setMode(new IdleMode(_terminal));
    return super.turnOn();
  }

  @Override
  public boolean setOnSilent() {
    return false;
  }

  @Override
  public void setBusy() {
    _terminal.setPrevious(this);
    _terminal.setMode(new BusyMode(_terminal));
  }

  @Override
  public String toString() {
    return "SILENCE";
  }
}
