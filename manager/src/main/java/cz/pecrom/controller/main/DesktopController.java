package cz.pecrom.controller.main;

import cz.pecrom.controller.*;
import cz.pecrom.ui.menu.*;
import cz.pecrom.view.*;
import cz.pecrom.view.main.form.*;

import javax.swing.*;
import javax.xml.bind.*;
import java.beans.*;
import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: pecrom
 * Date: 8.12.13
 * Time: 14:56
 */
public class DesktopController extends AbstractController {
  protected JFrame mainFrame = null;
  public DesktopController(AbstractView view, String formClazz) throws ClassNotFoundException {
    super(view, null, formClazz, null);
    setMainController(this);
    try {
      createMainDesktop();
    } catch (IOException | IllegalAccessException | InstantiationException e) {
      getLogger().info("Can not instatiate main desktop");
    }
  }

  private void createMainDesktop() throws IOException, IllegalAccessException, InstantiationException {
    JComponent form = (JComponent) getFormClazz().newInstance();
    getView().setContent(form);
    getView().getContent().setVisible(true);
    getView().getContent().requestFocus();
    mainFrame = new JFrame();

    mainFrame.add(getView().getContent());
    try(InputStream is =getClass().getResourceAsStream(getClass().getSimpleName()+".xml")){
      createMenu(is);
    }
    mainFrame.setVisible(true);
    mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    mainFrame.setSize(1280,760);
  }

  public void createInternalFrame(AbstractController controller){
    JInternalFrame frame = ((Desktop_Form)getView().getContent()).addInternalFrame(controller.getView().getContent());
    controller.getView().setFrame(frame);
    controller.getView().createActionPanel(controller.getView().getActions());
    getLogger().info("instatiating new internal frame");
  }

  protected JFrame getMainFrame() {
    return mainFrame;
  }

  @Override
  protected void initModel() {

  }

  protected void createMenu(InputStream resource) {
    try {
      JAXBContext context = JAXBContext.newInstance(Menu.class);
      Unmarshaller unmarshaller = context.createUnmarshaller();
      Menu menu = (Menu) unmarshaller.unmarshal(resource);
      menuBar = new JMenuBar();

      for (MenuItem item : menu.getMenuItem()) {
        JMenu mainMenuItem = createMainMenu(menuBar, item.getLabel());
        createMenuItems(mainMenuItem, item.getChildren());
        createAppMenuItems(mainMenuItem, item.getApplication());
      }
      getMainFrame().setJMenuBar(menuBar);
    } catch (JAXBException e) {
      getLogger().info("Cannot parse class");
    }
  }


  @Override
  protected Void doInBackground() throws Exception {
    return null;
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {

  }
}
