package prr.core.terminal.terminalMode;

public class OffMode implements TerminalMode {

    private static OffMode _offMode;

    private OffMode() {}


    public static OffMode getInstance() {
        if (_offMode == null) {
            return new OffMode();
        }

        return _offMode;
    }
    
}
