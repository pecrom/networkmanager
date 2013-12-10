package cz.pecrom.ui;

import java.beans.*;

/**
 * Created by IntelliJ IDEA.
 * User: pecrom
 * Date: 10.12.13
 * Time: 12:27
 */
public interface ViewChangeListener {
  public void addViewChangeListener(PropertyChangeListener listener);
  public void removeViewChangeListener(PropertyChangeListener listener);
}
