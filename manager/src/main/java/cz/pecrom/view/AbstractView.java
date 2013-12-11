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
public abstract class AbstractView implements PropertyChangeListener {
  final protected PropertyChangeSupport pcs = new PropertyChangeSupport(this);
  protected JComponent content = null;

  public JComponent getContent() {
    return content;
  }

  public void setContent(JComponent content) {
    this.content = content;
    initContent();
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

  public void addPropertyChangeListener(PropertyChangeListener listener){
    pcs.addPropertyChangeListener(listener);
  }

  public void removePropertyChangeListener(PropertyChangeListener listener){
    pcs.removePropertyChangeListener(listener);
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {

  }

  abstract protected void initContent();
}
