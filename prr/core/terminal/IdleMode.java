package prr.core.terminal;

public class IdleMode extends TerminalMode {

  public IdleMode(Terminal terminal) {
    super(terminal);
  }

  @Override
  public String toString() {
    return "IDLE";
  }
}
