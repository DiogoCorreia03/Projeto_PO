package prr.core.communication;

import prr.core.client.clientlevels.ClientLevel;
import prr.core.terminal.Terminal;

public abstract class InteractiveCommunication extends Communication {

  private int _duration;

  public InteractiveCommunication(int id, Terminal origin, Terminal receiver) {
    super(id, origin, receiver, true);
  }

  public double endCommunication(int size, ClientLevel level) {
    _duration = size;
    _isOngoing = false;
    _origin.setPreviousMode();
    _receiver.setPreviousMode();
    _origin.sendNotifications();
    _receiver.sendNotifications();
    double cost = computeCost(level, _origin.isFriend(_receiver.getId()));
    _cost = cost;
    return cost;
  }

  public int getSize() {
    return _duration;
  }
}
