package cz.pecrom.model;

import java.beans.*;

/**
 * Created by IntelliJ IDEA.
 * User: pecrom
 * Date: 9.12.13
 * Time: 19:49
 */
public abstract class AbstractModel {
  protected final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

  public void addPropertyChangeListener(PropertyChangeListener listener){
    pcs.addPropertyChangeListener(listener);
  }

  public void removePropertyChangeListener(PropertyChangeListener listener){
    pcs.removePropertyChangeListener(listener);
  }

}
