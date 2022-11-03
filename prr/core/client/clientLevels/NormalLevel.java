package prr.core.client.clientLevels;

import prr.core.client.Client;
import prr.core.communication.TextCommunication;
import prr.core.communication.VideoCommunication;
import prr.core.communication.VoiceCommunication;

public class NormalLevel implements ClientLevel {

  public final static NormalLevel _normalLevel = new NormalLevel();
  
  public double computeCost(TextCommunication comm) {
    int size = comm.getSize();
    return size<50?10:(size<100?16:2*size);
  }

  public double computeCost(VoiceCommunication comm) {
    return 20 * comm.getSize();
  }

  public double computeCost(VideoCommunication comm) {
    return 30 * comm.getSize();
  }

  public void changeLevel(Client c) {
    if (c.getBalence() > 500)
      c.setLevel(GoldLevel._goldLevel);
  }

  @Override
    public String toString() {
        return "NORMAL";
    }
}
