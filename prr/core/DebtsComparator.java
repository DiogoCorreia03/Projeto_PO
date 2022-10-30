package prr.core;

import java.util.Comparator;

import prr.core.client.Client;

public class DebtsComparator implements Comparator<Client> {
    public int compare(Client c1, Client c2) {
        if (c1.getDebts() < c2.getDebts()) return -1;
        if (c1.getDebts() > c2.getDebts()) return 1;
        return 0;
    }
}
