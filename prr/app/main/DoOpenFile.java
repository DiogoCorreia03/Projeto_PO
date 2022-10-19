package prr.app.main;

import prr.core.NetworkManager;
import prr.core.exception.UnavailableFileException;

import java.io.FileNotFoundException;
import java.io.IOException;

import prr.app.exception.FileOpenFailedException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Command to open a file.
 * Command present in the Main Menu, used to load data from a previous
 * session on to the Network.
 * When selected asks the User for the name of the File that
 * stores said data.
 * Throws Exceptions when there's issues on the opening or processing of the File.
 */
class DoOpenFile extends Command<NetworkManager> {

  /**
   * Constructor of the Class.
   * Calls the constructor of the SuperClass passing the Command's label and
   * associated NetworkManager as arguments. Furthermore, asks the User for the File's name.
   * @param receiver NetworkManager associated with the Command.
   */
  DoOpenFile(NetworkManager receiver) {
    super(Label.OPEN_FILE, receiver);
    addStringField("fileName", Message.openFile());
  }
  
  /**
   * Execute method, overriding the SuperClass's one.
   * Gets the attribute previously given by the User and tries to load the data
   * from a previous session, catching Exceptions when there's issues on the opening
   * or processing of the File.
   * @see pt.tecnico.uilib.menus.Command#execute()
   * @throws FileOpenFailedException Exception thrown when there's an issue opening
   * or processing the file.
   * @exception FileNotFoundException Exception thrown when the program can't find the specified file.
   * @exception ClassNotFoundException
   * @exception IOException
   */
  @Override
  protected final void execute() throws CommandException {
      try {
        String fileName = stringField("fileName");
        _receiver.load(fileName);
      }
      catch (UnavailableFileException e) {
        throw new FileOpenFailedException(e);
      }
      catch (ClassNotFoundException e) {
        throw new FileOpenFailedException(e);
      }
  }
}
