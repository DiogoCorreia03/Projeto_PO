package prr.core.communication;

import prr.core.client.clientLevels.ClientLevel;

public class TextCommunication extends Communication{
  private String _message;

  public TextCommunication(int id, String msg) {
    super(id);
    _message = msg;
  }

  protected double computeCost(ClientLevel level) {
    return level.priceSMS(getSize());
  }

  protected int getSize() {
    return _message.length();
  }
}
