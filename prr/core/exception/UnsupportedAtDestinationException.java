package prr.core.exception;

public class UnsupportedAtDestinationException extends TerminalException {
  private String _type;

  public UnsupportedAtDestinationException(String id, String type) {
    super(id);
    _type = type;
  }

  public String getType() {
    return _type;
  }
}
