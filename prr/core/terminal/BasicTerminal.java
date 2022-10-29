package prr.core.terminal;

import prr.core.client.Client;
import prr.core.exception.DuplicateTerminalException;

public class BasicTerminal extends Terminal{

  public BasicTerminal(String id, Client owner) throws DuplicateTerminalException{
    super(id, owner);
  }

  public void makeVideoCall(Terminal receiver) {

  }

  protected void acceptVideoCall(Terminal origin) {

  }

  public String toString() {
    return "BASIC|" + super.toString(); 
  }
}
