package prr.core.terminal;

public class IdleMode extends TerminalMode {

  public IdleMode(Terminal terminal) {
    super(terminal);
  }

  @Override
  public boolean turnOff() {
    _terminal.setPrevious(this);
    _terminal.setMode(new OffMode(_terminal));
    return true;
  }

  @Override
  public boolean turnOn() {
    return false;
  }

  @Override
  public boolean setOnSilent() {
    _terminal.setPrevious(this);
    _terminal.setMode(new SilenceMode(_terminal));
    return true;
  }

  @Override
  public void setBusy() {
    _terminal.setPrevious(this);
    _terminal.setMode(new BusyMode(_terminal));
  }

  @Override
  public String toString() {
    return "IDLE";
  }
}
