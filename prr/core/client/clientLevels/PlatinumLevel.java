package prr.core.client.clientLevels;

import java.util.List;

import prr.core.client.Client;
import prr.core.communication.Communication;
import prr.core.communication.TextCommunication;
import prr.core.communication.VideoCommunication;
import prr.core.communication.VoiceCommunication;

public class PlatinumLevel implements ClientLevel {

    private static PlatinumLevel _platinumLevel;

    private PlatinumLevel(){};

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
            c.setLevel(NormalLevel.getInstance());
        else if (lastTwoCommunications(c.getReversedMadeCommunications()))
            c.setLevel(GoldLevel.getInstance());
    }

    private boolean lastTwoCommunications(List<Communication> list) {
        for (int i = 0; i < 2; i++)
            if (!(list.get(i) instanceof TextCommunication))
                return false;
        return true;
    }

    public static PlatinumLevel getInstance() {
        if (_platinumLevel == null)
            return new PlatinumLevel();

        return _platinumLevel;
    }

    @Override
    public String toString() {
        return "PLATINUM";
    }
}
