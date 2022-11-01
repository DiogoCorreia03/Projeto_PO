package prr.core.terminal;

import prr.core.client.Client;
import prr.core.communication.Communication;
import prr.core.exception.DuplicateTerminalException;
import prr.core.exception.TerminalException;

public class FancyTerminal extends Terminal {

  public FancyTerminal(String id, Client owner) throws DuplicateTerminalException {
    super(id, owner);
  }

  public Communication makeVideoCall(Terminal receiver, int id) throws TerminalException {
    return getMode().makeVideoCall(receiver, id);
  }

  protected Communication acceptVideoCall(Terminal origin, int id) throws TerminalException {
    return getMode().acceptVideoCall(origin, id);
  }

  public String toString() {
    return "FANCY|" + super.toString();
  }
}
