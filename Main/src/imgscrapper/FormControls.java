package imgscrapper;

import javax.swing.*;


/**
 * @author Tomasz Pilarczyk
 */
public class FormControls {

    public void appendMessageToLogger(String message) {
        JScrollBar vertical = Main.frame.scrollPane.getVerticalScrollBar();
        Main.frame.loggerArea.append(message);
        vertical.setValue(vertical.getMaximum());
    }


}
