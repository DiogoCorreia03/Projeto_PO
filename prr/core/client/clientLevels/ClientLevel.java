package prr.core.client.clientLevels;

import java.io.Serializable;

public interface ClientLevel extends Serializable{
  static final long serialVersionUID = 202208091753L;

  public double priceSMS(int s);
  
  public double priceVoice(int d);

  public double priceVideo(int d);

  @Override
  public String toString();
}
