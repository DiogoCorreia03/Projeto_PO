package prr.core.communication;

import java.io.Serializable;

import prr.core.client.clientLevels.ClientLevel;
import prr.core.terminal.Terminal;

public abstract class Communication implements Serializable{
  
  /** Serial number for serialization. */
  private static final long serialVersionUID = 202208091753L;

  private int _id;

  private boolean _isPaid;

  double _cost;

  boolean _isOngoing;

  private Terminal _origin;

  private Terminal _receiver;

  public Communication(int id) {
    _id = id;
    //FIXME incompleto?
  }

  public void makeSMS(Terminal receiver, String msg) {

  }

  protected void acceptSMS(Terminal origin) {

  }

  public void makeVoiceCall(Terminal receiver) {

  }

  protected void acceptVoiceCall(Terminal origin) {

  }

  public String toString() {
    return null;
  }

  protected abstract double computeCost(ClientLevel level);

  protected abstract int getSize();
}
