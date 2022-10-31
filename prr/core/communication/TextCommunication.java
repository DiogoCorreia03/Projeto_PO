package prr.core.communication;

import prr.core.client.clientLevels.ClientLevel;
import prr.core.terminal.Terminal;

public class TextCommunication extends Communication{
  
  private String _message;

  public TextCommunication(int id, Terminal origin, Terminal receiver, String msg, ClientLevel level) {
    super(id, origin, receiver);
    _message = msg;
    _cost = computeCost(level);
  }

  public double endCommunication(int i, ClientLevel level) {
    return 0;
  }

  protected double computeCost(ClientLevel level) {
    return level.computeCost(this);
  }

  public int getSize() {
    return _message.length();
  }

  @Override
  public String toString() {
    return "TEXT|" + super.toString();
  }
}
