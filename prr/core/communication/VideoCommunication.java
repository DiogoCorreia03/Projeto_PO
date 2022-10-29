package prr.core.communication;

import prr.core.client.clientLevels.ClientLevel;

public class VideoCommunication extends InteractiveCommunication{
  
  public VideoCommunication(int id) {
    super(id);
  }

  protected double computeCost(ClientLevel level) {
    return level.priceVideo(getSize());
  }
}
