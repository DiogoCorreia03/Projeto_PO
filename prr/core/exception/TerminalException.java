package prr.core.exception;

public class TerminalException extends Exception {

    private final String _id;
    
    public TerminalException(String id) {
        _id = id;
    }

    public String getId() {
        return _id;
    }
}
