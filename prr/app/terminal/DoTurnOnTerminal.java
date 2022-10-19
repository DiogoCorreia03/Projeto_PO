package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import prr.core.TerminalMode;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Turn on the terminal.
 */
class DoTurnOnTerminal extends TerminalCommand {

  DoTurnOnTerminal(Network context, Terminal terminal) {
    super(Label.POWER_ON, context, terminal);
  }
  
  @Override
  protected final void execute() throws CommandException {
    if (_receiver.getMode() == TerminalMode.IDLE) {
      _display.popup(Message.alreadyOn());
    }
    else if (_receiver.canTurnOnTerminal()) {
      _receiver.turnOn();
    }
    //FIXME implement command. Exceptions??
  }
}
