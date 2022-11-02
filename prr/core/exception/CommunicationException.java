package prr.core.exception;

public class CommunicationException extends Exception {
    /** Serial number for serialization. */
  private static final long serialVersionUID = 202208091753L;

  private final int _key;

  public CommunicationException(int key) {
    _key = key;
  }

  public int getKey() {
    return _key;
  }
}
