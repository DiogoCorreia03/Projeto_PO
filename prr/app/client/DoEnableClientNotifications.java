package prr.app.client;

import prr.core.Network;
import prr.core.exception.UnknownClientException;
import prr.app.exception.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Enable client notifications.
 */
class DoEnableClientNotifications extends Command<Network> {

  DoEnableClientNotifications(Network receiver) {
    super(Label.ENABLE_CLIENT_NOTIFICATIONS, receiver);
    addStringField("key", Message.key());
    //FIXME add command fields
  }
  
  @Override
  protected final void execute() throws CommandException {
    try {
      String key = stringField("key");
      if (_receiver.showClient(key).getNotificationPreference())
        _display.popup(Message.clientNotificationsAlreadyEnabled());
      
      _receiver.showClient(key).enableNotifications();
    }
    catch (UnknownClientException e) {
      throw new UnknownClientKeyException(e.getKey());
    }
  }
}
