package prr.core.client.clientLevels;

import java.util.List;

import prr.core.client.Client;
import prr.core.communication.Communication;
import prr.core.communication.TextCommunication;
import prr.core.communication.VideoCommunication;
import prr.core.communication.VoiceCommunication;

public class PlatinumLevel implements ClientLevel {

    public final static PlatinumLevel _platinumLevel = new PlatinumLevel();

    public double computeCost(TextCommunication comm) {
        return comm.getSize()<50?0:4;
    }

    public double computeCost(VoiceCommunication comm) {
        return 10 * comm.getSize();
    } 

    public double computeCost(VideoCommunication comm) {
        return 10 * comm.getSize();
    }

    public void changeLevel(Client c) {
        if (c.getBalence() < 0)
            c.setLevel(NormalLevel._normalLevel);
        else if (lastTwoCommunications(c.getMadeCommunications()))
            c.setLevel(GoldLevel._goldLevel);
    }

    private boolean lastTwoCommunications(List<Communication> list) {
        for (int i = list.size(); i > list.size() - 2; --i)
            if (!(list.get(i) instanceof TextCommunication))
                return false;
        return true;
    }

    @Override
    public String toString() {
        return "PLATINUM";
    }
}
