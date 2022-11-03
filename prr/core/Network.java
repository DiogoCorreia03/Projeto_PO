package prr.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.io.IOException;

import prr.core.client.Client;
import prr.core.client.DebtsComparator;
import prr.core.communication.Communication;
import prr.core.exception.DuplicateClientException;
import prr.core.exception.DuplicateTerminalException;
import prr.core.exception.UnknownClientException;
import prr.core.exception.InvalidTerminalException;
import prr.core.exception.TerminalBusyException;
import prr.core.exception.TerminalOffException;
import prr.core.exception.TerminalSilenceException;
import prr.core.exception.UnknownTerminalException;
import prr.core.exception.UnrecognizedEntryException;
import prr.core.exception.UnsupportedAtDestinationException;
import prr.core.exception.UnsupportedAtOriginException;
import prr.core.notifications.Notification;
import prr.core.terminal.BasicTerminal;
import prr.core.terminal.FancyTerminal;
import prr.core.terminal.Terminal;

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

  /** Communications of the Network */
  private List<Communication> _communications = new ArrayList<>();

  /**
   * Obtains a certain client.
   * Receives the key of a Client, and gets the client associated to that key.
   * @param key Key of the Client.
   * @return  Client associated to that key.
   * @throws UnknownClientException Exception thrown when the given
   * Key isn't registered in the Network.
   */
  private Client getClient(String key) throws UnknownClientException {
    String keyLower = key.toLowerCase();
    if (!_clients.containsKey(keyLower))
      throw new UnknownClientException(key);
    return _clients.get(keyLower);
  }


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
    String keyLower = key.toLowerCase();
    if (_clients.containsKey(keyLower))
      throw new DuplicateClientException(key);
    Client client = new Client(key, name, nif);
    _clients.put(keyLower, client);
  }

  /**
   * Show a Client from it's Key.
   * @param key Key of the Client.
   * @return Client associated with the given Key formatted to a String.
   * @throws UnknownClientException Exception thrown when the given
   * Key isn't registered in the Network.
   */
  public String showClient(String key) throws UnknownClientException {
    return getClient(key).toString();
  }

  /**
   * Shows all notifications of a certain Client.
   * @param key Key of the Client.
   * @return List of notifications of the Client.
   * @throws UnknownClientException Exception thrown when the given
   * Key isn't registered in the Network.
   */
  public List<Notification> showClientNotifications(String key) throws UnknownClientException {
    return getClient(key).showAllNotifications();
  }

  /**
   * Get a list of all the Client's in the Network.
   * @return list of all the Client's in the Network formatted to a String.
   */
  public List<String> showAllClients() {
    List<String> temp = new ArrayList<>();
    for (Client c: _clients.values())
      temp.add(c.toString());
    return temp;
  }

  /**
   * Enables nofications of a certain Client.
   * @param key Key of the Client.
   * @return Returns true if the operations suceeds.
   * @throws UnknownClientException Exception thrown when the given
   * Key isn't registered in the Network.
   */
  public boolean enableClientNotifications(String key) throws UnknownClientException {
    Client c = getClient(key);
    return  c.enableNotifications();
  }

  /**
   * Disables nofications of a certain Client.
   * @param key Key of the Client.
   * @return Returns true if the operations suceeds.
   * @throws UnknownClientException Exception thrown when the given
   * Key isn't registered in the Network.
   */
  public boolean disableClientNotifications(String key) throws UnknownClientException {
    Client c = getClient(key);
    return  c.disableNotifications();
  }

  /**
   * Obtains payments of a certain Client.
   * @param key Key of the Client.
   * @return Payments of the client
   * @throws UnknownClientException Exception thrown when the given
   * Key isn't registered in the Network.
   */
  public double getClientPayments(String key) throws UnknownClientException{
    Client c = getClient(key);
    return c.getPayments();
  }

  /**
   * Obtains debts of a certain Client.
   * @param key Key of the Client.
   * @return Debts of the Client.
   * @throws UnknownClientException Exception thrown when the given
   * Key isn't registered in the Network.
   */
  public double getClientDebts(String key) throws UnknownClientException{
    Client c = getClient(key);
    return c.getDebts();
  }

  /**
   * Shows all clients with debts.
   * @return List of all the clients with debts, in the Network, formatted to a String.
   */
  public List<String> showClientsWithDebts() {
    List<Client> list = new ArrayList<>(_clients.values());
    Collections.sort(list, new DebtsComparator());
    List<String> ordered = new ArrayList<>();

    for (Client c : list) {
      ordered.add(c.toString());
    }

    return ordered;
  }

  /**
   * Shows all clients without debts.
   * @return List of all the clients without debts, in the Network, formatted to a String.
   */
  public List<String> showClientsWithoutDebts() {
    List<String> temp = new ArrayList<>();
    
    for (Client c : _clients.values())
      if (c.getDebts() == 0)
        temp.add(c.toString());

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
    String keyLower = clientKey.toLowerCase();
    if (_terminals.containsKey(id))
      throw new DuplicateTerminalException(id);
    if (id.length()!=6 || !id.matches("[0-9]+"))
      throw new InvalidTerminalException(id);
    if (!_clients.containsKey(keyLower))
      throw new UnknownClientException(clientKey);

    Client owner = _clients.get(keyLower);

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
   * @return a list of all the Terminals in the Network, formatted to a String.
   */
  public List<String> showAllTerminals() {
    List<String> temp = new ArrayList<>();
    for (Terminal t: _terminals.values())
      temp.add(t.toString());
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
  public List<String> getNoActivityTerminals() {
    List<String> list = new ArrayList<>();
    for (Terminal t: _terminals.values())
      if (!t.isActiveTerminal())
        list.add(t.toString());

    return list;
  }

  /**
   * Shows all terminals with positive balance in the Network.
   * @return List of all the terminals with Positive Balance in the Network, formatted to a String.
   */
  public List<String> showTerminalsWithPositiveBalance() {
    List<String> temp = new ArrayList<>();
    
    for (Terminal t : _terminals.values()) {
      if (t.getPayments() > t.getDebt())
        temp.add(t.toString());
    }

    return temp;
  }

  /**
   * Obtains all communications in the Network.
   * @return List of all the communications in the Network, formatted to a String.
   */
  public List<String> getAllComms() {
    List<String> list = new ArrayList<>();
    for (Communication c: _communications)
      list.add(c.toString());
    return list;
  }

  /**
   * Sends a text Communication.
   * Receives an origin terminal, who sends the text message;
   * receives a key that is associated to the Terminal receiver 
   * of the text message and receives a String containing the text message.
   * @param origin Terminal that sends the text message.
   * @param receiverId Key of the Terminal that receives the message.
   * @param msg String with the message.
   * @throws TerminalOffException Exception is thrown if the Terminal associated to the Key is off.
   * @throws UnknownTerminalException Exception thrown when the given Key isn't
   * registered in the Network.
   */
  public void sendTextCommunication(Terminal origin, String receiverId, String msg) throws TerminalOffException, UnknownTerminalException{
    Terminal receiver = getTerminal(receiverId);
    _communications.add(origin.makeSMS(receiver, msg, _communications.size() + 1));
  }

  /**
   * Sends an interactive Communication.
   * * Receives an origin terminal, who sends the text message;
   * receives a key that is associated to the Terminal receiver 
   * of the text message and receives a String containing the type of the message.
   * @param origin Terminal that sends the text message.
   * @param receiverId Key of the Terminal that receives the message.
   * @param type String that contains the type of message.
   * @throws TerminalOffException Exception is thrown if the Terminal associated to the Key is off.
   * @throws TerminalBusyException Exception is thrown if the Terminal associated to the Key is busy.
   * @throws TerminalSilenceException Exception is thrown if the Terminal associated to the Key is silent.
   * @throws UnsupportedAtOriginException Exception is thrown if the origin Terminal does not support the Communication.
   * @throws UnsupportedAtDestinationException Exception is thrown if the receiver Terminal associated to the Key does not support the Communication.
   * @throws UnknownTerminalException Exception thrown when the given Key isn't
   * registered in the Network.
   */
  public void startInteractiveCommunication(Terminal origin, String receiverId, String type) throws TerminalOffException, TerminalBusyException, TerminalSilenceException, UnsupportedAtOriginException, UnsupportedAtDestinationException, UnknownTerminalException{
    if (origin.getId() == receiverId)
      throw new TerminalBusyException(receiverId);
      
    Terminal receiver = getTerminal(receiverId);
    Communication interactiveComm = null;
    switch (type) {
      case "VOICE" -> interactiveComm = origin.makeVoiceCall(receiver, _communications.size() + 1);
      case "VIDEO" -> interactiveComm = origin.makeVideoCall(receiver, _communications.size() + 1);
    }
    _communications.add(interactiveComm);
  }

  /**
   * Shows all communications that a certain Client made.
   * @param id Key of the Client.
   * @return List of all the communications made by a certain client.
   * @throws UnknownClientException Exception thrown when the given
   * Key isn't registered in the Network.
   */
  public List<String> showCommunicationsfromClient(String id) throws UnknownClientException {
      Client c = getClient(id);
      List<Communication> temp = new ArrayList<>(c.getMadeCommunications());

      List<String> ordered = new ArrayList<>();
      for (Communication comm : temp)
        ordered.add(comm.toString());
      return ordered;  
  }

  /**
   Shows all communications that a certain Client received.
   * @param id Key of the Client.
   * @return List of all the communications received by a certain client.
   * @throws UnknownClientException Exception thrown when the given
   * Key isn't registered in the Network.
   */
  public List<String> showCommunicationsToClient(String id) throws UnknownClientException {
      Client c = getClient(id);
      List<Communication> temp = new ArrayList<>(c.getReceivedCommunications());

      List<String> ordered = new ArrayList<>();
      for (Communication comm : temp)
        ordered.add(comm.toString());
      return ordered;     
  }

  /**
   * Shows global payments.
   * Adds the payments of all the clients in the Network.
   * @return Double containing the value of global payments.
   */
  public double showGlobalPayments() {
    double sum_payments = 0;
    for (Client c : _clients.values())
        sum_payments += c.getPayments();

    return sum_payments;
  }

  /**
   * Shows global debts.
   * Adds the debts of all the clients in the Network.
   * @return Double containing the value of global debts.
   */
  public double showGlobalDebts() {
    double sum_debts = 0;
    for (Client c : _clients.values())
        sum_debts += c.getDebts();

    return sum_debts;
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

