package cz.pecrom.controller.apps.sniffer;

import cz.pecrom.controller.*;
import cz.pecrom.controller.main.*;
import org.jnetpcap.*;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: pecrom
 * Date: 8.12.13
 * Time: 15:39
 */
public class SnifferController extends InternalController {

  public SnifferController(String clazz, DesktopController desktop) throws ClassNotFoundException {
    super(clazz, desktop);
    init();
  }

  private void init(){
//    System.load("C:\\jnetpcap\\jnetpcap.dll");
    initAllDevs();

  }

  private void initAllDevs(){
    List<PcapIf> devs = new ArrayList<>();
    StringBuilder sb = new StringBuilder();
    Pcap.findAllDevs(devs, sb);
    for(PcapIf dev : devs){
      for(PcapAddr addr : dev.getAddresses())
        System.out.println(addr.getAddr().getFamily());
    }
  }
}
