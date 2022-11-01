package prr.core.terminal;

import prr.core.client.clientLevels.ClientLevel;
import prr.core.communication.Communication;
import prr.core.exception.TerminalOffException;

public class OffMode extends TerminalMode {
    
    public OffMode(Terminal terminal) {
        super(terminal);
    }
    
    @Override
    public Communication acceptSMS(Terminal origin, String msg, int id, ClientLevel level) throws TerminalOffException {
        throw new TerminalOffException(_terminal.getId());
    }
    
    @Override
    public Communication acceptVoiceCall(Terminal origin, int id) throws TerminalOffException {
        throw new TerminalOffException(_terminal.getId());
    }
    
    @Override
    public Communication acceptVideoCall(Terminal origin, int id) throws TerminalOffException {
        throw new TerminalOffException(_terminal.getId());
    }
    
    @Override
    public String toString() {
        return "OFF";
    }
}
