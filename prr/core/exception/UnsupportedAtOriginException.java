package prr.core.exception;

public class UnsupportedAtOriginException extends TerminalException {
  private String _type;

  public UnsupportedAtOriginException(String id, String type) {
    super(id);
    _type = type;
  }

  public String getType() {
    return _type;
  }
}
