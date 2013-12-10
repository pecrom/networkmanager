package cz.pecrom.controller.apps.sniffer;

import cz.pecrom.controller.*;
import cz.pecrom.controller.main.*;
import cz.pecrom.model.*;
import cz.pecrom.model.apps.sniffer.*;
import cz.pecrom.network.providers.*;
import org.jnetpcap.*;

import java.beans.*;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: pecrom
 * Date: 8.12.13
 * Time: 15:39
 */
public class SnifferController extends InternalController {


  public SnifferController(String viewClazz, AbstractModel model, DesktopController desktop) throws ClassNotFoundException {
    super(viewClazz, model, desktop);

  }


  private List<org.jnetpcap.PcapIf> initAllDevs() throws Exception {
    List<org.jnetpcap.PcapIf> devs = PcapProvider.getInterfaces();
    return devs;
  }

  private PcapSockAddr getIPv4Addr(List<PcapAddr> addresses) {
    if (!addresses.isEmpty()) {
      int i = 0;
      while (i < addresses.size()) {
        if (addresses.get(i).getAddr().getFamily() == PcapSockAddr.AF_INET)
          return addresses.get(i).getAddr();
        else
          i++;
      }
    }
    return null;
  }

  private String getDevicesIPv4Address(PcapIf inf) {
    return getIPv4Addr(inf.getAddresses()).toString();
  }

  @Override
  protected void initModel() {
    getModel().addPropertyChangeListener((PropertyChangeListener) getView());
    try {
      List<PcapIf> infs = initAllDevs();
      if (!infs.isEmpty()) {
        for (PcapIf inf : initAllDevs())
          ((SnifferModel) getModel()).addAdapter(inf);
        ((SnifferModel) getModel()).setAddress(getDevicesIPv4Address(infs.get(0)));
      }


    } catch (Exception e) {
      getLogger().info("cannot init model: " + e.getMessage());
    }

  }
}
