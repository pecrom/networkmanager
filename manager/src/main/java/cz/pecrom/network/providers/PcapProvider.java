package cz.pecrom.network.providers;

import org.jnetpcap.*;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: pecrom
 * Date: 9.12.13
 * Time: 15:30
 */
public class PcapProvider {
  public static List<org.jnetpcap.PcapIf> getInterfaces() throws Exception {
    List<org.jnetpcap.PcapIf> devs = new ArrayList<>();
    StringBuilder sb = new StringBuilder();
    int result = Pcap.findAllDevs(devs, sb);
    if(result == 0)
      return devs;
    else
      throw new Exception("Can not find all network devices");
  }
}
