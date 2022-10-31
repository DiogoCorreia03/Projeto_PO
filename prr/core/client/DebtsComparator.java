package prr.core.client;

import java.util.Comparator;

public class DebtsComparator implements Comparator<Client> {
    public int compare(Client c1, Client c2) {
        return Double.compare(c2.getDebts(), c1.getDebts());
    }
}
