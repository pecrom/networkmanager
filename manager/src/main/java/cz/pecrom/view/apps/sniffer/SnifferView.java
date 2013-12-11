package cz.pecrom.view.apps.sniffer;

import cz.pecrom.apps.sniffer.view.*;
import cz.pecrom.model.apps.sniffer.*;
import cz.pecrom.view.*;
import cz.pecrom.view.apps.sniffer.form.*;
import org.jnetpcap.*;

import java.beans.*;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: pecrom
 * Date: 10.12.13
 * Time: 15:37
 */
public class SnifferView extends AbstractView {



  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    switch(evt.getPropertyName()){
      case SnifferModel.ADAPTERS:
        ((Sniffer_Form)getContent()).removeAllItemsAdapters();
        for(PcapIf inf : (Set<PcapIf>)evt.getNewValue()){
          ((Sniffer_Form)getContent()).addItemAdapters(inf);
        }
        break;
    }
  }

  @Override
  protected void initContent() {
    ((Sniffer_Form)getContent()).setAdaptersRenderer(new InterfaceComboRenderer());
  }
}
