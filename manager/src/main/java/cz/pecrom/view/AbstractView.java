package cz.pecrom.view;

import cz.pecrom.controller.*;

import javax.swing.*;
import java.awt.*;
import java.beans.*;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: pecrom
 * Date: 10.12.13
 * Time: 15:22
 */
public abstract class AbstractView<T extends AbstractController> implements PropertyChangeListener {
  final protected PropertyChangeSupport pcs = new PropertyChangeSupport(this);
  protected JComponent content = null;
  protected JInternalFrame frame = null;
  protected T controller;
  protected Map<String, AbstractAction> actions;

  protected AbstractView() {
    actions = new HashMap<>();
    initActions();
  }

  public JComponent getContent() {
    return content;
  }

  public void setContent(JComponent content) {
    this.content = content;
    initContent();
  }

  public T getController() {
    return controller;
  }

  public void setController(T controller) {
    this.controller = controller;
  }

  protected void addAction(String label, AbstractAction action) {
    actions.put(label, action);
  }

  protected void addAction(Map.Entry<String, AbstractAction> entry) {
    actions.entrySet().add(entry);
  }

  public Map<String, AbstractAction> getActions() {
    return actions;
  }

  public void setActions(Map<String, AbstractAction> actions) {
    this.actions = actions;
  }

  public void createActionPanel(Map<String, AbstractAction> actions) {
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
    for (Map.Entry<String, AbstractAction> entry : actions.entrySet()) {
      JButton actionButton = new JButton();
      actionButton.setAction(entry.getValue());
      actionButton.setText(entry.getKey());
      buttonPanel.add(actionButton);
    }

    getFrame().getContentPane().add(buttonPanel, BorderLayout.SOUTH);
  }

  public JInternalFrame getFrame() {
    return frame;
  }

  public void setFrame(JInternalFrame frame) {
    this.frame = frame;
  }

  public void addPropertyChangeListener(PropertyChangeListener listener) {
    pcs.addPropertyChangeListener(listener);
  }

  public void removePropertyChangeListener(PropertyChangeListener listener) {
    pcs.removePropertyChangeListener(listener);
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {

  }

  abstract protected void initContent();

  abstract protected void initActions();
}
