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

  public Communication(int id, Terminal origin, Terminal receiver) {
    _id = id;
    _origin = origin;
    _receiver = receiver;
    //FIXME incompleto?
  }

  public void makeSMS(Terminal receiver, String msg) {
    //FIXME pq?
  }

  protected void acceptSMS(Terminal origin) {
    //FIXME pq?

  }

  public void makeVoiceCall(Terminal receiver) {
    //FIXME pq?
  }

  protected void acceptVoiceCall(Terminal origin) {
    //FIXME pq?
  }

  public String toString() {
    return "TEXT|" + _id +"|"+ _origin.getId() +"|"+ _receiver.getId() +"|0|"+ _cost +"|"+ _isOngoing;
  }

  protected abstract double computeCost(ClientLevel level);

  protected abstract int getSize();
}
