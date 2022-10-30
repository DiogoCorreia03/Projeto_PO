package prr.core.terminal;

import prr.core.client.Client;
import prr.core.communication.Communication;
import prr.core.exception.DuplicateTerminalException;
import prr.core.exception.UnsupportedAtDestinationException;
import prr.core.exception.UnsupportedAtOriginException;

public class BasicTerminal extends Terminal{

  public BasicTerminal(String id, Client owner) throws DuplicateTerminalException{
    super(id, owner);
  }

  public Communication makeVideoCall(Terminal receiver, int id) throws UnsupportedAtOriginException {
    throw new UnsupportedAtOriginException(getId(), "VIDEO");
  }

  protected Communication acceptVideoCall(int id, Terminal origin) throws UnsupportedAtDestinationException{
    throw new UnsupportedAtDestinationException(getId(), "VIDEO");
  }

  public String toString() {
    return "BASIC|" + super.toString(); 
  }
}
