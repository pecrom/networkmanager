package cz.pecrom.view.apps.sniffer;

import cz.pecrom.apps.sniffer.view.*;
import cz.pecrom.controller.apps.sniffer.*;
import cz.pecrom.model.apps.sniffer.*;
import cz.pecrom.ui.component.combo.*;
import cz.pecrom.view.*;
import cz.pecrom.view.apps.sniffer.form.*;
import org.jnetpcap.*;

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

  private void updateAdapters(Set<PcapIf> adapters){
    ((Sniffer_Form)getContent()).removeAllItemsAdapters();
    for(PcapIf inf : adapters){
      ((Sniffer_Form)getContent()).addItemAdapters(inf);
    }
  }

  private void updateAddress(String address){
    ((Sniffer_Form)getContent()).setAddress(address);
  }

  private void updateNetmask(String netmask){
    ((Sniffer_Form)getContent()).setNetmask(netmask);
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    switch(evt.getPropertyName()){
      case SnifferModel.ADAPTERS:
        updateAdapters((Set<PcapIf>) evt.getNewValue());
        break;
      case SnifferModel.ADDRESS:
        updateAddress((String)evt.getNewValue());
        break;
      case SnifferModel.NETMASK:
        updateNetmask((String) evt.getNewValue());
        break;
    }
  }

  @Override
  protected void initContent() {
    ((Sniffer_Form)getContent()).setAdaptersRenderer(new InterfaceComboRenderer());
    ((Sniffer_Form)getContent()).addItemSelectionListener(new AdapterSelectionListener(this));
  }


  class AdapterSelectionListener extends ComboSelectionListener<SnifferView>{
    public AdapterSelectionListener(SnifferView view) {
      super(view);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
      getView().getController().changeSelectedAdapter((PcapIf)e.getItem());
    }
  }
}
