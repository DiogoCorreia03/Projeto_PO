package prr.core.communication;

import prr.core.client.clientlevels.ClientLevel;
import prr.core.terminal.Terminal;

public class VideoCommunication extends InteractiveCommunication {

  public VideoCommunication(Terminal origin, Terminal receiver, int id) {
    super(id, origin, receiver);
  }

  protected double computeCost(ClientLevel level, boolean isFriend) {
    return isFriend?level.computeCost(this)/2:level.computeCost(this);
  }

  @Override
  public String toString() {
    return "VIDEO|" + super.toString();
  }
}
