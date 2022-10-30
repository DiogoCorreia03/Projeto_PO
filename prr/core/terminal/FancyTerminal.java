package prr.core.terminal;

import prr.core.client.Client;
import prr.core.communication.Communication;
import prr.core.communication.VideoCommunication;
import prr.core.exception.DuplicateTerminalException;
import prr.core.exception.TerminalBusyException;
import prr.core.exception.TerminalException;
import prr.core.exception.TerminalOffException;
import prr.core.exception.TerminalSilenceException;

public class FancyTerminal extends Terminal{

  public FancyTerminal(String id, Client owner) throws DuplicateTerminalException{
    super(id, owner);
  }

  public Communication makeVideoCall(Terminal receiver, int id) throws TerminalException{
    Communication videoComm = receiver.acceptVideoCall(id, this);
    setOngoingCommunication(videoComm);
    setBusy();
    addMadeCommunication(videoComm);
    return videoComm;
  }

  protected Communication acceptVideoCall(int id, Terminal origin) throws TerminalException{
    if (getMode() == TerminalMode.OFF)
    throw new TerminalOffException(getId());
  if (getMode() == TerminalMode.BUSY)
    throw new TerminalBusyException(getId());
  if (getMode() == TerminalMode.SILENCE)
    throw new TerminalSilenceException(getId());

  Communication voiceComm = new VideoCommunication(id, origin, this);
  setOngoingCommunication(voiceComm);
  setBusy();
  addReceivedCommunication(voiceComm);
  return voiceComm;
}

  public String toString() {
    return "FANCY|" + super.toString();
  }
}
