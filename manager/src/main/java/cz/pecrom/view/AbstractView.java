package cz.pecrom.view;

import javax.swing.*;
import java.awt.*;
import java.beans.*;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: pecrom
 * Date: 10.12.13
 * Time: 15:22
 */
public class AbstractView implements PropertyChangeListener {
  protected JComponent content = null;

  public JComponent getContent() {
    return content;
  }

  public void setContent(JComponent content) {
    this.content = content;
  }

  public void setActions(List<AbstractAction> actions){
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
    for(AbstractAction action : actions){
      JButton actionButton = new JButton("test");
      actionButton.setAction(action);
      buttonPanel.add(actionButton);
    }
    getContent().add(buttonPanel, BorderLayout.SOUTH);
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {

  }
}
