package prr.core;

import prr.core.exception.DuplicateTerminalException;

public class FancyTerminal extends Terminal{

  public FancyTerminal(String id, Client owner) throws DuplicateTerminalException{
    super(id, owner);
  }

  public void makeVideoCall(Terminal receiver) {

  }

  public void acceptVideoCall(Terminal origin) {

  }

  public String toString() {
    return "FANCY|" + super.toString(); 
  }
}
