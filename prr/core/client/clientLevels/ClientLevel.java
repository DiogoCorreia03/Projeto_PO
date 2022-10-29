package prr.core.client.clientLevels;

public interface ClientLevel {

  public double priceSMS(int s);
  
  public double priceVoice(int d);

  public double priceVideo(int d);
}
