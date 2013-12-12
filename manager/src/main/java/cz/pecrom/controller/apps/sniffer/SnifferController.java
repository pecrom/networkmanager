package cz.pecrom.controller.apps.sniffer;

import cz.pecrom.controller.*;
import cz.pecrom.controller.main.*;
import cz.pecrom.model.*;
import cz.pecrom.model.apps.sniffer.*;
import cz.pecrom.network.*;
import cz.pecrom.network.providers.*;
import cz.pecrom.network.sniffer.*;
import cz.pecrom.network.sniffer.packetcapturesupport.*;
import cz.pecrom.view.*;
import org.jnetpcap.*;
import org.jnetpcap.packet.*;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: pecrom
 * Date: 8.12.13
 * Time: 15:39
 */
public class SnifferController extends InternalController implements PacketCapturedListener {
  private PacketCapturer capturer;


  public SnifferController(AbstractView view, AbstractModel model, String formClazz, DesktopController desktop) throws ClassNotFoundException {
    super(view, model, formClazz, desktop);

  }


  private List<org.jnetpcap.PcapIf> initAllDevs() throws Exception {
    List<org.jnetpcap.PcapIf> devs = PcapProvider.getInterfaces();
    return devs;
  }

  private PcapAddr findIPv4Addr(List<PcapAddr> addresses) {
    if (!addresses.isEmpty()) {
      int i = 0;
      while (i < addresses.size()) {
        if (addresses.get(i).getAddr().getFamily() == PcapSockAddr.AF_INET)
          return addresses.get(i);
        else
          i++;
      }
    }
    return null;
  }

  private PcapSockAddr getIPv4Addr(List<PcapAddr> addresses) {
    PcapAddr address = findIPv4Addr(addresses);
    return address != null ? address.getAddr() : null;
  }


  private String getDevicesNetmask(PcapIf inf) {
    if (inf != null) {
      PcapAddr ipv4addr = findIPv4Addr(inf.getAddresses());
      return ipv4addr != null ? ipv4addr.getNetmask().toString() : null;
    }
    return null;
  }


  private String getDevicesIPv4Address(PcapIf inf) {
    if (inf != null) {
      PcapSockAddr ipv4addr = getIPv4Addr(inf.getAddresses());
      return ipv4addr != null ? ipv4addr.toString() : null;
    }
    return null;
  }

  @Override
  protected void initModel() {
    getModel().addPropertyChangeListener(getView());
    try {
      List<PcapIf> infs = initAllDevs();
      if (!infs.isEmpty()) {
        for (PcapIf inf : infs)
          ((SnifferModel) getModel()).addAdapter(inf);
      }


    } catch (Exception e) {
      getLogger().info("cannot init model: " + e.getMessage());
    }

  }

  public void changeSelectedAdapter(PcapIf newAdapter) {
    ((SnifferModel) getModel()).setSelectedAdapter(newAdapter);
    ((SnifferModel) getModel()).setAddress(NetworkUtils.parseIPv4(getDevicesIPv4Address(newAdapter)));
    ((SnifferModel) getModel()).setNetmask(NetworkUtils.parseIPv4(getDevicesNetmask(newAdapter)));
  }

  public void startCapturing() {
    capturer = new PacketCapturer(((SnifferModel) getModel()).getSelectedAdapter());
    capturer.addPacketCapturedListener(this);
    capturer.setState(PacketCapturer.State.CAPTURE);
  }

  public void stopCapturing() {
    getLogger().info("stopping");
    capturer.setState(PacketCapturer.State.STOP);
  }

  @Override
  public void packetCaptured(PcapIf inf, PcapPacket packet) {
    ((SnifferModel) getModel()).addPacket(packet);
  }
}
