package prr.core.communication;

import prr.core.client.clientLevels.ClientLevel;
import prr.core.terminal.Terminal;

public class VideoCommunication extends InteractiveCommunication {

  public VideoCommunication(Terminal origin, Terminal receiver, int id) {
    super(id, origin, receiver);
  }

  protected double computeCost(ClientLevel level) {
    return level.computeCost(this);
  }

  @Override
  public String toString() {
    return "VIDEO|" + super.toString();
  }
}
