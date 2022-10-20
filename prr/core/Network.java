package prr.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.io.IOException;

import prr.core.exception.DuplicateClientException;
import prr.core.exception.DuplicateTerminalException;
import prr.core.exception.UnknownClientException;
import prr.core.exception.InvalidTerminalException;
import prr.core.exception.UnknownTerminalException;
import prr.core.exception.UnrecognizedEntryException;

/**
 * Class Store implements a store.
 * Class that implements all the functionalities of the App.
 * Stores all the relevant Objects and implements methods on them.
 * Can be Serializable to save it's current state and load it on a different session.
 * @author Diogo Correia IST1103198
 * @author João Santos IST1102746
 */
public class Network implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202208091753L;

  /** Clients of the Network. */
  private Map<String, Client> _clients = new TreeMap<>();

  /** Terminals of the Network. */
  private Map<String, Terminal> _terminals = new TreeMap<>();


  /**
   * Register a Client in the Network.
   * Receives all the attributes needed to register a Client, verifies them and
   * creates a new Client in the Network.
   * @param key Key of the Client.
   * @param name Name of the Client.
   * @param nif TaxNumber of the Client.
   * @throws DuplicateClientException Exception thrown when the Client's Key is
   * already registered in the Network.
   */
  public void registerClient(String key, String name, int nif) throws DuplicateClientException {
    if (_clients.containsKey(key))
      throw new DuplicateClientException(key);
    Client client = new Client(key, name, nif);
    _clients.put(key, client);
  }

  /**
   * Get a Client from it's Key.
   * @param key Key of the Client.
   * @return the Client associated with the given Key.
   * @throws UnknownClientException Exception thrown when the given
   * Key isn't registered in the Network.
   */
  public Client showClient(String key) throws UnknownClientException {
    if (!_clients.containsKey(key))
      throw new UnknownClientException(key);
    return _clients.get(key);
  }

  /**
   * Get a list of all the Client's in the Network.
   * @return a list of all the Client's in the Network.
   */
  public List<Client> showAllClients() {
    List<Client> temp = new ArrayList<>(_clients.values());
    return temp;
  }

  /**
   * Register a Terminal in the Network.
   * Receives all the attributes needed to register a Terminal, verifies them and
   * creates a new Terminal in the Network.
   * @param id Key of the Terminal.
   * @param type Type of the Terminal.
   * @param clientKey Key of the Owner of the Terminal.
   * @return the instance of the registered Terminal
   * @throws DuplicateTerminalException Exception thrown when the Terminal's Key is
   * already registered in the Network.
   * @throws InvalidTerminalException Exception thrown when Terminal's doesn't match the specified format.
   * @throws UnknownClientException Exception thrown when the Owner's Key isn't registered in the Network.
   */
  public Terminal registerTerminal(String id, String type, String clientKey) throws DuplicateTerminalException, InvalidTerminalException, UnknownClientException{
    if (_terminals.containsKey(id))
      throw new DuplicateTerminalException(id);
    if (id.length()!=6 || !id.matches("[0-9]+"))
      throw new InvalidTerminalException(id);
    if (!_clients.containsKey(clientKey))
      throw new UnknownClientException(clientKey);

    Client owner = _clients.get(clientKey);

    switch(type) {
      case "BASIC":
        BasicTerminal basicTerminal = new BasicTerminal(id, owner);
        _terminals.put(id, basicTerminal);
        owner.addTerminal(basicTerminal);
        return basicTerminal;
      case "FANCY":
        FancyTerminal fancyTerminal = new FancyTerminal(id, owner);
        _terminals.put(id, fancyTerminal);
        owner.addTerminal(fancyTerminal);
        return fancyTerminal;
    }
    return null;
  }

  /**
   * Get a Terminal from it's Key.
   * @param id Key of the Terminal.
   * @return the Terminal associated with the given Key.
   * @throws UnknownTerminalException Exception thrown when the given Key isn't
   * registered in the Network.
   */
  public Terminal getTerminal(String id) throws UnknownTerminalException{
    if (!_terminals.containsKey(id))
      throw new UnknownTerminalException(id);
    return _terminals.get(id);
  }

  /**
   * Get a list of all the Terminals in the Network.
   * @return a list of all the Terminals in the Network.
   */
  public List<Terminal> showAllTerminals() {
    List<Terminal> temp = new ArrayList<>(_terminals.values());
    return temp;
  }

  /**
   * Add a friend to a specified Terminal.
   * Checks if the to be added friend's Key is registered in the Network. If
   * it is, adds said terminal to the friends list.
   * @param terminal Key of the Terminal on which the friend is getting added.
   * @param friend Key of the Terminal that is getting added as a friend.
   * @throws UnknownTerminalException Exception thrown when the Key for the terminal
   * to be added as a friend isn't registered in the Network.
   */
  public void addFriend(String terminal, String friend) throws UnknownTerminalException{
    if (!_terminals.containsKey(friend) || !_terminals.containsKey(terminal))
      throw new UnknownTerminalException(friend);
      
    _terminals.get(terminal).addTerminalFriend(_terminals.get(friend));
  }

  /**
   * Remove a friend from a specified Terminal.
   * Checks if the to be removed friend's Key is registered in
   * the Network. If it is, removes it from the specified Terminal's
   * friends list.
   * @param terminal Key of the Terminal from which the friend is getting removed.
   * @param friend Key of the Terminal that is getting removed as a friend.
   * @throws UnknownTerminalException Exception thrown when the Key for the terminal
   * to be removed as a friend isn't registered in the Network.
   */
  public void removeFriend(String terminal, String friend) throws UnknownTerminalException{
    if (!_terminals.containsKey(friend))
      throw new UnknownTerminalException(friend);
      
    _terminals.get(terminal).removeTerminalFriend(friend);
  }

  /**
   * Iterate through all the Terminals in the Network and check if
   * they have done any past Communications. If they haven't then
   * they get added to the Array to be returned.
   * @return an Array of all the Terminals with no past Communications.
   */
  public List<Terminal> getNoActivityTerminals() {
    List<Terminal> list = new ArrayList<>();
    for (Terminal t: _terminals.values())
      if (!t.isActiveTerminal())
        list.add(t);

    return list;
  }

  /**
   * Read text input file and create corresponding domain entities.
   * 
   * @param filename name of the text input file
   * @throws UnrecognizedEntryException if some entry is not correct
   * @throws IOException if there is an IO erro while processing the text file
   */
  void importFile(String filename) throws UnrecognizedEntryException, IOException {
    Parser parser = new Parser(this);
    parser.parseFile(filename);
  }  
}

