package cz.pecrom.network.sniffer.exceptions;

import org.jnetpcap.*;

/**
 * Created by IntelliJ IDEA.
 * User: pecrom
 * Date: 12.12.13
 * Time: 13:45
 */
public class CaptureInProgressException extends Exception {
  private PcapIf inf;
  private String message;

  public CaptureInProgressException(PcapIf inf, String message){
    this.inf = inf;
    this.message = message;
  }

  public PcapIf getInf() {
    return inf;
  }

  public void setInf(PcapIf inf) {
    this.inf = inf;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
