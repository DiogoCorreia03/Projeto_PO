package prr.core.communication;

import prr.core.terminal.Terminal;

public abstract class InteractiveCommunication extends Communication{
  
  private int _duration;

  public InteractiveCommunication(int id, Terminal origin, Terminal receiver) {
    super(id, origin, receiver);
    //FIXME duracao é dada logo q uma comunicao é feita ou so mais tarde?
  }

  protected int getSize() {
    return _duration;
  }
}
