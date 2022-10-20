package prr.core.exception;

public class TerminalException extends Exception {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202208091753L;

    private final String _id;
    
    public TerminalException(String id) {
        _id = id;
    }

    public String getId() {
        return _id;
    }
}
