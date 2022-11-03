package prr.core.client.clientLevels;

import java.io.Serializable;

import prr.core.client.Client;
import prr.core.communication.TextCommunication;
import prr.core.communication.VideoCommunication;
import prr.core.communication.VoiceCommunication;

public interface ClientLevel extends Serializable{
  static final long serialVersionUID = 202208091753L;

  public double computeCost(TextCommunication comm);
  
  public double computeCost(VoiceCommunication comm);

  public double computeCost(VideoCommunication comm);

  public void changeLevel(Client c);
}
