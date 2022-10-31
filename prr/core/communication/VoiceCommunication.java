package prr.core.communication;

import prr.core.client.clientLevels.ClientLevel;
import prr.core.terminal.Terminal;

public class VoiceCommunication extends InteractiveCommunication {

  public VoiceCommunication(Terminal origin, Terminal receiver, int id) {
    super(id, origin, receiver);
  }

  protected double computeCost(ClientLevel level) {
    return level.computeCost(this);
  }

  @Override
  public String toString() {
    return "VOICE|" + super.toString();
  }
}
