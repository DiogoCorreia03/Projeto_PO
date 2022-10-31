package prr.core.terminal.terminalMode;

public class BusyMode implements TerminalMode {

    private static BusyMode _busyMode;

    private BusyMode() {}


    public static BusyMode getInstance() {
        if (_busyMode == null) {
            return new BusyMode();
        }

        return _busyMode;
    }
    
}
