package imgscrapper;


import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;


/**
 * @author Tomasz Pilarczyk
 */
public class Frame extends JFrame {
    private JPanel panel;
    private JTextField textField1 = new JTextField();
    public JScrollPane scrollPane;
    public JTextArea loggerArea;
    private JLabel label;
    public JComboBox albumCountComboBox;
    private JLabel albumLabel;
    public JButton stopButton;
    private JButton clearButton;
    public JComboBox timePeriodComboBox;
    private JButton fileButton;
    private JTextField destinationTextField;
    private JButton downloadButton2;
    private JButton openButton;
    private JFileChooser fc;
    ImagesFinder gImages;
    public Thread t;

    Frame() {

        $$$setupUI$$$();
        setWindowBounds();
        setTitle("imgurScrapper");
        textField1.setText("What are you looking for");
        albumCountComboBox.setSelectedIndex(1);

        add(panel);
        addWindowListener(exitListener(t));
        clearButtonListener();
        stopButtonListener();
        fileButtonListener();
        downloadButtonListener();
        openButtonListener();

        setVisible(true);

    }

    private void setWindowBounds() {
        Dimension screenResolution = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenResolution.getWidth() / 3;
        int height = (int) screenResolution.getHeight() / 3;
        int posX = (int) (screenResolution.getWidth() / 2) - (width / 2);
        int posY = (int) (screenResolution.getHeight() / 2) - (height / 2);
        setSize(new Dimension(width, height));
        setLocation(new Point(posX, posY));
    }

    private WindowListener exitListener(final Thread t) {
        WindowListener exitListener = new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showOptionDialog(
                        null, "Are You Sure to Close Application?",
                        "Exit Confirmation", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == 0) {
                    System.exit(0);
                    ImagesFinder.stop();
                }
            }
        };
        return exitListener;
    }

    public void clearButtonListener() {
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int confirm = JOptionPane.showOptionDialog(
                        null, "Are you sure you want to delete ALL files from download folder?",
                        "Delete Confirmation", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == 0) {
                    deleteAllFiles();
                }

            }
        });
    }

    public void deleteAllFiles() {
        File dir = new File(ImagesDownloader.folderPath);
        for (File file : dir.listFiles())
            if (!file.isDirectory())
                file.delete();
    }

    public void stopButtonListener() {
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //t.stop();
                ImagesFinder.stop();
                loggerArea.append("\nStopped\n");
                stopButton.setEnabled(false);
            }
        });
    }

    public void fileButtonListener() {

        fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int returnVal = fc.showOpenDialog(Frame.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    ImagesDownloader.folderPath = file.getAbsolutePath();
                    destinationTextField.setText(ImagesDownloader.folderPath);
                }
            }
        });
    }

    public void downloadButtonListener() {
        downloadButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                String search = textField1.getText();
                File dir = new File(ImagesDownloader.folderPath);
                ImagesFinder.albumCount = (albumCountComboBox.getSelectedIndex() + 1);
                if (dir.isDirectory()) {
                    loggerArea.setText(null);
                    gImages = new ImagesFinder(search);
                    t = new Thread(gImages);
                    t.start();
                } else {
                    JOptionPane.showMessageDialog(Frame.this, "This is not directory");
                }
            }
        });
    }

    public void openButtonListener() {
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    Desktop.getDesktop().open(new File(ImagesDownloader.folderPath));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void createUIComponents() {

        String[] periodes = {"day", "week", "month", "year", "all"};
        timePeriodComboBox = new JComboBox(periodes);
        String[] tabs = {"1", "2", "3", "4", "5", "6", "7", "8"};
        albumCountComboBox = new JComboBox(tabs);


    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel = new JPanel();
        panel.setLayout(new GridLayoutManager(13, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel.setBackground(new Color(-12302519));
        panel1.add(panel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        textField1 = new JTextField();
        textField1.setText("");
        panel.add(textField1, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        scrollPane = new JScrollPane();
        panel.add(scrollPane, new GridConstraints(5, 0, 8, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        loggerArea = new JTextArea();
        loggerArea.setBackground(new Color(-13948117));
        loggerArea.setFocusable(true);
        loggerArea.setFont(new Font("Arial", loggerArea.getFont().getStyle(), 14));
        loggerArea.setForeground(new Color(-4473925));
        scrollPane.setViewportView(loggerArea);
        stopButton = new JButton();
        stopButton.setBackground(new Color(-12828863));
        stopButton.setEnabled(false);
        stopButton.setForeground(new Color(-4342339));
        stopButton.setSelected(false);
        stopButton.setText("Stop");
        panel.add(stopButton, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setForeground(new Color(-4342339));
        label1.setText("From");
        panel.add(label1, new GridConstraints(6, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panel.add(albumCountComboBox, new GridConstraints(5, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        albumLabel = new JLabel();
        albumLabel.setForeground(new Color(-4342339));
        albumLabel.setText("Albums");
        panel.add(albumLabel, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        clearButton = new JButton();
        clearButton.setBackground(new Color(-12828863));
        clearButton.setForeground(new Color(-4342339));
        clearButton.setText("Clear");
        panel.add(clearButton, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        fileButton = new JButton();
        fileButton.setBackground(new Color(-12828863));
        fileButton.setForeground(new Color(-4342339));
        fileButton.setText("Directory");
        panel.add(fileButton, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setForeground(new Color(-4473925));
        label2.setText("Choose Directory");
        panel.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setForeground(new Color(-4539718));
        label3.setText("Clear Directory");
        panel.add(label3, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        label = new JLabel();
        label.setFont(new Font(label.getFont().getName(), label.getFont().getStyle(), label.getFont().getSize()));
        label.setForeground(new Color(-4342339));
        label.setHorizontalAlignment(0);
        label.setHorizontalTextPosition(0);
        label.setText("           ImgurScrapper");
        panel.add(label, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        destinationTextField = new JTextField();
        destinationTextField.setBackground(new Color(-13948117));
        destinationTextField.setForeground(new Color(-4539718));
        panel.add(destinationTextField, new GridConstraints(3, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        panel.add(timePeriodComboBox, new GridConstraints(7, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        downloadButton2 = new JButton();
        downloadButton2.setBackground(new Color(-12894656));
        downloadButton2.setForeground(new Color(-4539718));
        downloadButton2.setText("Download");
        panel.add(downloadButton2, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        openButton = new JButton();
        openButton.setBackground(new Color(-12894656));
        openButton.setForeground(new Color(-4539718));
        openButton.setText("Open");
        panel.add(openButton, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }
}


