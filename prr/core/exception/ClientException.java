package prr.core.exception;

public class ClientException extends Exception{

  private final String _key;

  public ClientException(String key) {
    _key = key;
  }

  public String getKey() {
    return _key;
  }
}
