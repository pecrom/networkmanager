package cz.pecrom.view.apps.sniffer;

import cz.pecrom.apps.sniffer.view.*;
import cz.pecrom.controller.apps.sniffer.*;
import cz.pecrom.model.apps.sniffer.*;
import cz.pecrom.ui.component.combo.*;
import cz.pecrom.view.*;
import cz.pecrom.view.apps.sniffer.form.*;
import org.jnetpcap.*;
import org.jnetpcap.packet.*;
import org.jnetpcap.packet.format.*;
import org.jnetpcap.protocol.network.*;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;
import java.beans.*;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: pecrom
 * Date: 10.12.13
 * Time: 15:37
 */
public class SnifferView extends AbstractView<SnifferController> {
  private TableModel packetsModel;

  private void updateAdapters(Set<PcapIf> adapters) {
    ((Sniffer_Form) getContent()).removeAllItemsAdapters();
    for (PcapIf inf : adapters) {
      ((Sniffer_Form) getContent()).addItemAdapters(inf);
    }
  }

  private void updateAddress(String address) {
    ((Sniffer_Form) getContent()).setAddress(address);
  }

  private void updateNetmask(String netmask) {
    ((Sniffer_Form) getContent()).setNetmask(netmask);
  }

  private void updatePackets(Queue<PcapPacket> packets) {
    Ip4 ip4 = new Ip4();
    PcapPacket packet;
    while ((packet = packets.poll()) != null) {
      if (packet.hasHeader(ip4)) {
        ip4 = packet.getHeader(ip4);
        ((DefaultTableModel) packetsModel).addRow(new Object[]{
          FormatUtils.ip(ip4.destination()),
          FormatUtils.ip(ip4.source()),
          ip4.version()
        });
      }
    }
//    ((DefaultTableModel) packetsModel).addRow(test);
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    switch (evt.getPropertyName()) {
      case SnifferModel.ADAPTERS:
        updateAdapters((Set<PcapIf>) evt.getNewValue());
        break;
      case SnifferModel.ADDRESS:
        updateAddress((String) evt.getNewValue());
        break;
      case SnifferModel.NETMASK:
        updateNetmask((String) evt.getNewValue());
        break;
      case SnifferModel.PACKETS:
        updatePackets((Queue<PcapPacket>) evt.getNewValue());
        break;
    }
  }

  private Vector<String> createHeaderVector() {
    Vector<String> header = new Vector<>();
    header.add("Type");
    header.add("Source");
    header.add("Destination");
    return header;
  }

  private void initPacketsTable() {
    packetsModel = new DefaultTableModel(createHeaderVector(), 0);
    ((Sniffer_Form) getContent()).setPacketModel(packetsModel);
  }

  @Override
  protected void initContent() {
    initPacketsTable();
    ((Sniffer_Form) getContent()).setAdaptersRenderer(new InterfaceComboRenderer());
    ((Sniffer_Form) getContent()).addItemSelectionListener(new AdapterSelectionListener(this));


  }

  @Override
  protected void initActions() {
    addAction("Start", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        getController().startCapturing();
      }
    });

    addAction("Stop", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        getController().stopCapturing();
      }
    });
  }


  class AdapterSelectionListener extends ComboSelectionListener<SnifferView> {
    public AdapterSelectionListener(SnifferView view) {
      super(view);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
      getView().getController().changeSelectedAdapter((PcapIf) e.getItem());
    }
  }
}
