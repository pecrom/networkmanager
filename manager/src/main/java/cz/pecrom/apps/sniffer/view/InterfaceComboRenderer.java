package cz.pecrom.apps.sniffer.view;

import org.jnetpcap.*;

import javax.swing.*;
import javax.swing.plaf.basic.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: pecrom
 * Date: 10.12.13
 * Time: 11:10
 */
public class InterfaceComboRenderer extends BasicComboBoxRenderer {
  @Override
  public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
    //@ToDo fix it, sometimes value==null
    String pcapValue = (value instanceof String || value == null) ? (String) value : ((PcapIf) value).getDescription();
    return super.getListCellRendererComponent(list, pcapValue, index, isSelected, cellHasFocus);
  }
}
