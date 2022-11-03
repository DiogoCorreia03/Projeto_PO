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
        _terminal.addToNotify(origin.getOwner());
        throw new TerminalOffException(_terminal.getId());
    }

    @Override
    public Communication acceptVoiceCall(Terminal origin, int id) throws TerminalOffException {
        _terminal.addToNotify(origin.getOwner());
        throw new TerminalOffException(_terminal.getId());
    }

    @Override
    public Communication acceptVideoCall(Terminal origin, int id) throws TerminalOffException {
        _terminal.addToNotify(origin.getOwner());
        throw new TerminalOffException(_terminal.getId());
    }

    @Override
    public boolean turnOff() {
        return false;
    }

    public boolean turnOn() {
        _terminal.setPrevious(this);
        _terminal.setMode(new IdleMode(_terminal));
        return super.turnOn();
    }

    public boolean setOnSilent() {
        _terminal.setPrevious(this);
        _terminal.setMode(new SilenceMode(_terminal));
        return super.setOnSilent();
    }

    @Override
    public void setBusy() {
    }

    @Override
    public String toString() {
        return "OFF";
    }
}
