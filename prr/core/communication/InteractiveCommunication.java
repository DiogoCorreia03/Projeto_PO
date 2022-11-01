package prr.core.communication;

import prr.core.client.clientLevels.ClientLevel;
import prr.core.terminal.Terminal;

public abstract class InteractiveCommunication extends Communication {

  private int _duration;

  public InteractiveCommunication(int id, Terminal origin, Terminal receiver) {
    super(id, origin, receiver, true);
  }

  private void end() {
    _isOngoing = false;
    _origin.setPreviousMode();
    _receiver.setPreviousMode();
  }

  public double endCommunication(int size, ClientLevel level) {
    _duration = size;
    end();
    double cost = computeCost(level);
    setCost(cost);
    return cost;
  }

  public int getSize() {
    return _duration;
  }

  @Override
  public String toString() {
    return super.toString();
  }
}
