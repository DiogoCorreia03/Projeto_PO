package prr.core.terminal.terminalMode;

public class SilenceMode implements TerminalMode {

    private static SilenceMode _silenceMode;

    private SilenceMode() {}


    public static SilenceMode getInstance() {
        if (_silenceMode == null) {
            return new SilenceMode();
        }

        return _silenceMode;
    }
}
