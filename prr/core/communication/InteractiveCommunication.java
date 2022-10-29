package prr.core.communication;

public abstract class InteractiveCommunication extends Communication{
  private int _duration;

  public InteractiveCommunication(int id) {
    super(id);
    //FIXME duracao é dada logo q uma comunicao é feita ou so mais tarde?
  }

  protected int getSize() {
    return _duration;
  }
}
