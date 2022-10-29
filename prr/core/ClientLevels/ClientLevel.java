package prr.core.ClientLevels;

public interface ClientLevel {

  public double priceSMS(String msg);
  
  public double priceVoice(int d);

  public double priceVideo(int d);
}
