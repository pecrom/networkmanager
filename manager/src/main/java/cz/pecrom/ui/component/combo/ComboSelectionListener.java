package cz.pecrom.ui.component.combo;

import cz.pecrom.view.*;
import sun.reflect.generics.reflectiveObjects.*;

import java.awt.event.*;

/**
 * Created by IntelliJ IDEA.
 * User: pecrom
 * Date: 11.12.13
 * Time: 21:57
 */
public abstract class ComboSelectionListener<T extends AbstractView> implements ItemListener {
  T view;
  public ComboSelectionListener(T view){
    this.view = view;
  }

  public T getView() {
    return view;
  }

  public void setView(T view) {
    this.view = view;
  }

  @Override
  public void itemStateChanged(ItemEvent e) {
    throw new NotImplementedException();
  }
}
