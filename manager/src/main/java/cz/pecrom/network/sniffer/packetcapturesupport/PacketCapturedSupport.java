package cz.pecrom.network.sniffer.packetcapturesupport;

import org.jnetpcap.*;
import org.jnetpcap.packet.*;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: pecrom
 * Date: 12.12.13
 * Time: 15:04
 */
public class PacketCapturedSupport {
  private Set<PacketCapturedListener> listeners = new HashSet<>();

  public void addPacketCapturedListener(PacketCapturedListener listener){
    listeners.add(listener);
  }

  public void removePacketCapturedListener(PacketCapturedListener listener){
    listeners.remove(listener);
  }

  public void firePacketCaptured(PcapIf inf, PcapPacket packet){
    for(PacketCapturedListener listener : listeners)
      listener.packetCaptured(inf, packet);
  }

}
