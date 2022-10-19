package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import prr.core.TerminalMode;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Turn off the terminal.
 */
class DoTurnOffTerminal extends TerminalCommand {

  DoTurnOffTerminal(Network context, Terminal terminal) {
    super(Label.POWER_OFF, context, terminal);
  }
  
  @Override
  protected final void execute() throws CommandException {
    if (_receiver.getMode() == TerminalMode.OFF) {
      _display.popup(Message.alreadyOff());
    }
    else if (_receiver.canTurnOffTerminal()) {
      _receiver.turnOff();
    }
    //FIXME implement command. Exceptions??
  }
}
