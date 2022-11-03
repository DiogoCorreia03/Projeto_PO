package prr.app.terminal;

import prr.core.Network;
import prr.core.terminal.Terminal;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show balance.
 */
class DoShowTerminalBalance extends TerminalCommand {

  DoShowTerminalBalance(Network context, Terminal terminal) {
    super(Label.SHOW_BALANCE, context, terminal);
  }
  
  /**
   * @see pt.tecnico.uilib.menus.Command#execute()
   */
  @Override
  protected final void execute() throws CommandException {
    String id = _receiver.getId();
    double payments = _receiver.getPayments();
    double debts = _receiver.getDebt();
    _display.addLine(Message.terminalPaymentsAndDebts(id, Math.round(payments), Math.round(debts)));
    _display.display();
  }
}
