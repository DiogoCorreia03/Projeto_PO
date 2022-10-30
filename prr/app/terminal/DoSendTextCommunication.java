package prr.app.terminal;

import prr.core.Network;
import prr.core.exception.TerminalException;
import prr.core.terminal.Terminal;
import prr.app.exception.UnknownTerminalKeyException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Command for sending a text communication.
 */
class DoSendTextCommunication extends TerminalCommand {

  DoSendTextCommunication(Network context, Terminal terminal) {
    super(Label.SEND_TEXT_COMMUNICATION, context, terminal, receiver -> receiver.canStartCommunication());
    addStringField("key", Message.terminalKey());
    addStringField("message", Message.textMessage());
  }
  
  @Override
  protected final void execute() throws CommandException {
    try {
      _network.sendTextCommunication(_receiver, stringField("key"), stringField("message"));
    }
    catch (TerminalException e) {
      System.out.println("erro"); //FIXME fix nas exceptions
    }
  }
} 
