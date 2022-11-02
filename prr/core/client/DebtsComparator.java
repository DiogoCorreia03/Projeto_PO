package prr.core.client;

import java.io.Serializable;
import java.util.Comparator;

public class DebtsComparator implements Comparator<Client>, Serializable {
    /** Serial number for serialization. */
    private static final long serialVersionUID = 202208091753L;

    public int compare(Client c1, Client c2) {
        return Double.compare(c2.getDebts(), c1.getDebts());
    }
}
