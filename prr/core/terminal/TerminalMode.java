package prr.core.terminal;

import java.io.Serializable;

import prr.core.client.clientLevels.ClientLevel;
import prr.core.communication.Communication;
import prr.core.communication.TextCommunication;
import prr.core.communication.VideoCommunication;
import prr.core.communication.VoiceCommunication;
import prr.core.exception.TerminalException;

public abstract class TerminalMode implements Serializable {
  static final long serialVersionUID = 202208091753L;

  protected Terminal _terminal;

  public TerminalMode(Terminal terminal) {
    _terminal = terminal;
  }

  public Terminal getTerminal() {
    return _terminal;
  }

  public Communication makeSMS(Terminal receiver, String message, int id, ClientLevel level) throws TerminalException {
    Communication textComm = receiver.acceptSMS(_terminal, message, id, level);
    _terminal.addMadeCommunication(textComm);
    _terminal.addDebt(textComm.getCost());
    return textComm;
  }

  public Communication acceptSMS(Terminal origin, String msg, int id, ClientLevel level) throws TerminalException {
    Communication textComm = new TextCommunication(id, origin, _terminal, msg, level);
    _terminal.addReceivedCommunication(textComm);
    return textComm;
  }

  public Communication makeVoiceCall(Terminal receiver, int id) throws TerminalException {
    Communication voiceComm = receiver.acceptVoiceCall(_terminal, id);
    _terminal.setOngoingCommunication(voiceComm);
    _terminal.setPrevious(_terminal.getMode());
    _terminal.setBusy();
    _terminal.addMadeCommunication(voiceComm);
    return voiceComm;
  }

  public Communication acceptVoiceCall(Terminal origin, int id) throws TerminalException {
    Communication voiceComm = new VoiceCommunication(origin, _terminal, id);
    _terminal.setOngoingCommunication(voiceComm);
    _terminal.setPrevious(_terminal.getMode());
    _terminal.setBusy();
    _terminal.addReceivedCommunication(voiceComm);
    return voiceComm;
  }

  public Communication makeVideoCall(Terminal receiver, int id) throws TerminalException {
    Communication videoComm = receiver.acceptVideoCall(_terminal, id);
    _terminal.setOngoingCommunication(videoComm);
    _terminal.setPrevious(_terminal.getMode());
    _terminal.setBusy();
    _terminal.addMadeCommunication(videoComm);
    return videoComm;
  }

  public Communication acceptVideoCall(Terminal origin, int id) throws TerminalException {
    Communication videoComm = new VideoCommunication(origin, _terminal, id);
    _terminal.setOngoingCommunication(videoComm);
    _terminal.setBusy();
    _terminal.addReceivedCommunication(videoComm);
    return videoComm;
  }
}
