package prr.core.communication;

import prr.core.client.clientLevels.ClientLevel;
import prr.core.terminal.Terminal;

public class VideoCommunication extends InteractiveCommunication{
  
  public VideoCommunication(int id, Terminal origin, Terminal receiver) {
    super(id, origin, receiver);
  }

  protected double computeCost(ClientLevel level) {
    return level.priceVideo(getSize());
  }

  @Override
  public String toString() {
    return "VIDEO|" + super.toString();
  }
}
