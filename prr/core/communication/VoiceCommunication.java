package prr.core.communication;

import prr.core.client.clientLevels.ClientLevel;

public class VoiceCommunication extends InteractiveCommunication{
  
  public VoiceCommunication(int id) {
    super(id);
  }

  protected double computeCost(ClientLevel level) {
    return level.priceVoice(getSize());
  }
}
