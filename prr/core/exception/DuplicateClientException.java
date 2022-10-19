package prr.core.exception;

public class DuplicateClientException extends ClientException{
  public DuplicateClientException(String key) {
    super(key);
  }
}
