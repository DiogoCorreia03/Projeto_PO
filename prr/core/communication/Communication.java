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

  public boolean getPaymentStatus() {
    return _isPaid;
  }

  public double getCost() {
    return _cost;
  }

  @Override
  public String toString() {
    return _id +"|"+ _origin.getId() +"|"+ _receiver.getId() +"|"+ getSize() +"|"+ Math.round(_cost) +"|"+ (_isOngoing?"ONGOING":"FINISHED");
  }

  protected abstract double computeCost(ClientLevel level);

  protected abstract int getSize();
}
