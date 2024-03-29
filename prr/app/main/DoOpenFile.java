package prr.app.main;

import prr.core.NetworkManager;
import prr.core.exception.UnavailableFileException;

import prr.app.exception.FileOpenFailedException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Command to open a file.
 */
class DoOpenFile extends Command<NetworkManager> {

  DoOpenFile(NetworkManager receiver) {
    super(Label.OPEN_FILE, receiver);
    addStringField("fileName", Message.openFile());
  }
  
  /**
   * @see pt.tecnico.uilib.menus.Command#execute()
   */
  @Override
  protected final void execute() throws CommandException {
      try {
        String fileName = stringField("fileName");
        _receiver.load(fileName);
      }
      catch (UnavailableFileException | ClassNotFoundException e) {
        throw new FileOpenFailedException(e);
      }
  }
}
