package prr.core.communication;

import prr.core.client.clientLevels.ClientLevel;
import prr.core.terminal.Terminal;

public class TextCommunication extends Communication{
  
  private String _message;

  public TextCommunication(int id, Terminal origin, Terminal receiver, String msg) {
    super(id, origin, receiver);
    _message = msg;
  }

  protected double computeCost(ClientLevel level) {
    return level.priceSMS(getSize());
  }

  protected int getSize() {
    return _message.length();
  }
}
