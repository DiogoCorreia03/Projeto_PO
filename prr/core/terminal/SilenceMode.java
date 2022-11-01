package prr.core.terminal;

import prr.core.communication.Communication;
import prr.core.exception.TerminalSilenceException;

public class SilenceMode extends TerminalMode {

  public SilenceMode(Terminal terminal) {
    super(terminal);
  }

  @Override
  public Communication acceptVoiceCall(Terminal origin, int id) throws TerminalSilenceException {
    throw new TerminalSilenceException(_terminal.getId());
  }

  @Override
  public Communication acceptVideoCall(Terminal origin, int id) throws TerminalSilenceException {
    throw new TerminalSilenceException(_terminal.getId());
  }

  @Override
  public String toString() {
    return "SILENCE";
  }
}
