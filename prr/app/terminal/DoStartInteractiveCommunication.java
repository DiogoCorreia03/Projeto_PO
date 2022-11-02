package prr.app.terminal;

import prr.core.Network;
import prr.core.exception.TerminalBusyException;
import prr.core.exception.TerminalOffException;
import prr.core.exception.TerminalSilenceException;
import prr.core.exception.UnknownTerminalException;
import prr.core.exception.UnsupportedAtDestinationException;
import prr.core.exception.UnsupportedAtOriginException;
import prr.core.terminal.Terminal;
import prr.app.exception.UnknownTerminalKeyException;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Command for starting communication.
 */
class DoStartInteractiveCommunication extends TerminalCommand {

  DoStartInteractiveCommunication(Network context, Terminal terminal) {
    super(Label.START_INTERACTIVE_COMMUNICATION, context, terminal, receiver -> receiver.canStartCommunication());
    addStringField("key", Message.terminalKey());
    addOptionField("type", Message.commType(), "VOICE", "VIDEO");
  }
  
  @Override
  protected final void execute() throws CommandException {
    try {
      _network.startInteractiveCommunication(_receiver, stringField("key"), stringField("type"));
    }
    catch (UnknownTerminalException e) {
      throw new UnknownTerminalKeyException(e.getId());
    }
    catch (TerminalOffException e) {
      _display.popup(Message.destinationIsOff(e.getId()));
    }
    catch (TerminalSilenceException e) {
      _display.popup(Message.destinationIsSilent(e.getId()));
    }
    catch (TerminalBusyException e) {
      _display.popup(Message.destinationIsBusy(e.getId()));
    }
    catch (UnsupportedAtOriginException e) {
      _display.popup(Message.unsupportedAtOrigin(e.getId(), e.getType()));
    }
    catch (UnsupportedAtDestinationException e) {
      _display.popup(Message.unsupportedAtDestination(e.getId(), e.getType()));
    }
  }
}
