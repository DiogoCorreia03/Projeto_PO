package prr.core.communication;

import prr.core.client.clientLevels.ClientLevel;
import prr.core.terminal.Terminal;

public class VoiceCommunication extends InteractiveCommunication{
  
  public VoiceCommunication(int id, Terminal origin, Terminal receiver) {
    super(id, origin, receiver);
  }

  protected double computeCost(ClientLevel level) {
    return level.priceVoice(getSize());
  }
}
