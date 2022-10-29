package prr.core;

import prr.core.exception.DuplicateTerminalException;

public class BasicTerminal extends Terminal{

  public BasicTerminal(String id, Client owner) throws DuplicateTerminalException{
    super(id, owner);
  }

  public void makeVideoCall(Terminal receiver) {

  }

  public void acceptVideoCall(Terminal origin) {

  }

  public String toString() {
    return "BASIC|" + super.toString(); 
  }
}
