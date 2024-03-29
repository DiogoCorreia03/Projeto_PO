package prr.core.client.clientlevels;

import java.util.List;

import prr.core.client.Client;
import prr.core.communication.Communication;
import prr.core.communication.TextCommunication;
import prr.core.communication.VideoCommunication;
import prr.core.communication.VoiceCommunication;

public class GoldLevel implements ClientLevel {
    
    public final static GoldLevel _goldLevel = new GoldLevel();

    public double computeCost(TextCommunication comm) {
        int size = comm.getSize();

        return size<100?10:size*2;
    }

    public double computeCost(VoiceCommunication comm) {
        return 10 * comm.getSize();
    }

    public void changeLevel(Client c) {
        List<Communication> list = c.getReversedMadeCommunications();
        if (c.getBalence() < 0)
            c.setLevel(NormalLevel._normalLevel);
        else if (list.size() >= 5 && lastFiveCommunications(list))
            c.setLevel(PlatinumLevel._platinumLevel);
    }

    private boolean lastFiveCommunications(List<Communication> list) {
        for (int i = 0; i < 5; i++)
            if (!(list.get(i) instanceof VideoCommunication) || list.get(i).isOngoing())
                return false;
        return true;
    }

    public double computeCost(VideoCommunication comm) {
        return 20 * comm.getSize();
    }

    @Override
    public String toString() {
        return "GOLD";
    }
}
