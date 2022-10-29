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

  protected double computeCost(ClientLevel level) {
    return level.priceSMS(getSize());
  }

  protected int getSize() {
    return _message.length();
  }

  @Override
  public String toString() {
    return "TEXT|" + super.toString();
  }
}
