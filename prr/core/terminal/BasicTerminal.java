package prr.core.terminal;

import prr.core.client.Client;
import prr.core.communication.Communication;
import prr.core.exception.DuplicateTerminalException;

public class BasicTerminal extends Terminal{

  public BasicTerminal(String id, Client owner) throws DuplicateTerminalException{
    super(id, owner);
  }

  public Communication makeVideoCall(Terminal receiver, int id) {
    return null;
  }

  protected Communication acceptVideoCall(Terminal origin) {
    return null;
  }

  public String toString() {
    return "BASIC|" + super.toString(); 
  }
}
