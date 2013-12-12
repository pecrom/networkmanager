package cz.pecrom.model.apps.sniffer;

import cz.pecrom.model.*;
import org.jnetpcap.*;
import org.jnetpcap.packet.*;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: pecrom
 * Date: 9.12.13
 * Time: 19:48
 */
public class SnifferModel extends AbstractModel {
  final public static String ADAPTERS = "adapters";
  final public static String SELECTED_ADAPTER = "selectedAdapter";
  final public static String PACKETS = "packets";
  final public static String ADDRESS = "address";
  final public static String NETMASK = "netmask";

  Queue<PcapPacket> packetQueue;{
    packetQueue = new LinkedList<>();
  }

  Set<org.jnetpcap.PcapIf> adapters;{
    adapters = new HashSet<>();
  }
  PcapIf selectedAdapter = null;
  List<PcapPacket> packets;{
    packets = new ArrayList<>();
  }
  String address;
  String netmask;

  public Set<PcapIf> getAdapters() {
    return adapters;
  }

  public void addAdapter(org.jnetpcap.PcapIf adapter) {
    Set<org.jnetpcap.PcapIf> oldAdapters = new HashSet<>(adapters);
    adapters.add(adapter);
    pcs.firePropertyChange(ADAPTERS, oldAdapters, adapters);
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    String oldAddress = getAddress();
    this.address = address;
    pcs.firePropertyChange(ADDRESS, oldAddress, address);
  }

  public String getNetmask() {
    return netmask;
  }

  public void setNetmask(String netmask) {
    String oldNetmask = getNetmask();
    this.netmask = netmask;
    pcs.firePropertyChange(NETMASK, oldNetmask, netmask);
  }

  public List<PcapPacket> getPackets() {

    return packets;

  }

  public void addPacket(PcapPacket packet) {
    List<PcapPacket> oldPackets = new ArrayList<>(packets);
    packets.add(packet);
    packetQueue.add(packet);
    pcs.firePropertyChange(PACKETS, null, packetQueue);
  }

  public PcapIf getSelectedAdapter() {
    return selectedAdapter;
  }

  public void setSelectedAdapter(PcapIf selectedAdapter) {
    PcapIf oldSelectedAdapter = getSelectedAdapter();
    this.selectedAdapter = selectedAdapter;
    pcs.firePropertyChange(SELECTED_ADAPTER, oldSelectedAdapter, selectedAdapter);
  }
}
