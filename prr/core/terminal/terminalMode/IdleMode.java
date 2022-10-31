package prr.core.terminal.terminalMode;

import java.util.List;

import prr.core.client.Client;
import prr.core.client.clientLevels.ClientLevel;
import prr.core.communication.Communication;
import prr.core.communication.TextCommunication;
import prr.core.exception.TerminalOffException;
import prr.core.terminal.Terminal;

public class IdleMode implements TerminalMode {

    private static IdleMode _idleMode;

    private IdleMode() {}

    public Communication makeSMS(Terminal receiver, String message, int id, Client owner, Terminal origin) {
        Communication textComm = receiver.acceptSMS(id, this, message, origin.getOwner().getClientLevel());
        origin.addMadeCommunication(textComm);
        origin.addDebt(textComm.getCost());
        return textComm;
    }

    public Communication acceptSMS(int id, Terminal origin, String msg, ClientLevel level) throws TerminalOffException {
          //FIXME adicionar mandar/criar notificacao
    
        Communication textComm = new TextCommunication(id, origin, this, msg, level);
        _receivedCommunications.add(textComm);
        return textComm;
      }





    public static IdleMode getInstance() {
        if (_idleMode == null) {
            return new IdleMode();
        }

        return _idleMode;
    }

    public String toString() {
        return "SILENCE";
    }
    
}
