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
    try {
      Communication videoComm = getMode().makeVideoCall(receiver, id);
      setOngoingCommunication(videoComm);
      setPrevious(getMode());
      setBusy();
      addMadeCommunication(videoComm);
      return videoComm;
    }
    catch (TerminalException e) {
      throw e;
    }
  }

  protected Communication acceptVideoCall(Terminal origin, int id) throws TerminalException {
    try {
      Communication videoComm = getMode().acceptVideoCall(origin, id);
      setOngoingCommunication(videoComm);
      setBusy();
      addReceivedCommunication(videoComm);
      return videoComm;
    }
    catch (TerminalException e) {
      throw e;
    }
  }

  public String toString() {
    return "FANCY|" + super.toString();
  }
}
