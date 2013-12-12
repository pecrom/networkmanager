package cz.pecrom.network.sniffer.packetcapturesupport;

import org.jnetpcap.*;
import org.jnetpcap.packet.*;

/**
 * Created by IntelliJ IDEA.
 * User: pecrom
 * Date: 12.12.13
 * Time: 15:05
 */
public interface PacketCapturedListener {
  public void packetCaptured(PcapIf inf, PcapPacket packet);
}
