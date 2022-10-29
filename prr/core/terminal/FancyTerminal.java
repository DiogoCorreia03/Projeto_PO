package prr.core.terminal;

import prr.core.client.Client;
import prr.core.exception.DuplicateTerminalException;

public class FancyTerminal extends Terminal{

  public FancyTerminal(String id, Client owner) throws DuplicateTerminalException{
    super(id, owner);
  }

  public void makeVideoCall(Terminal receiver) {

  }

  protected void acceptVideoCall(Terminal origin) {

  }

  public String toString() {
    return "FANCY|" + super.toString(); 
  }
}
