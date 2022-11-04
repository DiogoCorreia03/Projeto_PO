package prr.core.communication;

import java.io.Serializable;

import prr.core.client.clientlevels.ClientLevel;
import prr.core.terminal.Terminal;

public abstract class Communication implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202208091753L;

  private int _id;

  protected boolean _isPaid;

  double _cost;

  boolean _isOngoing;

  protected Terminal _origin;

  protected Terminal _receiver;

  public Communication(int id, Terminal origin, Terminal receiver) {
    _id = id;
    _origin = origin;
    _receiver = receiver;
  }

  public Communication(int id, Terminal origin, Terminal receiver, boolean state) {
    _id = id;
    _origin = origin;
    _receiver = receiver;
    _isOngoing = state;
  }

  public int getId() {
    return _id;
  }

  public double getCost() {
    return _cost;
  }

  protected void setCost(double cost) {
    _cost = cost;
  }

  public boolean isPaid() {
    return _isPaid;
  }

  public boolean isOngoing() {
    return _isOngoing;
  }

  public void Pay() {
    _isPaid = true;
  }

  public boolean isOrigin(String key) {
    return key.equals(_origin.getId());
  }

  @Override
  public String toString() {
    return _id + "|" + _origin.getId() + "|" + _receiver.getId() + "|" + getSize() + "|" + Math.round(_cost) + "|"
        + (_isOngoing ? "ONGOING" : "FINISHED");
  }

  public abstract double endCommunication(int size, ClientLevel level);

  protected abstract double computeCost(ClientLevel level, boolean isFriend);

  public abstract int getSize();
}
