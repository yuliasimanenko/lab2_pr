import lab1.Umbrella;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowEvent;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.*;
import java.util.stream.Stream;

public class ClientForm2 extends JFrame {
    private JPanel panel1;
    private JTextField inputCountry;
    private JTextField inputColor;
    private JCheckBox useFiltersCheckBox;
    private JButton updateCollectionButton;
    private JSlider slider1;
    private JList list1;
    private JButton startButton;
    private JButton stopButton;
    private JComboBox comboBox1;
    private JLabel color;
    private JLabel filters;
    private JLabel country;
    private JLabel yearf;
    private JPanel Client;

    Object[] umbrellasArray = new Object[0];
    boolean collectionDrawn = false;
    ArrayList<Object> objsToHide = new ArrayList<>();
    boolean isHidingElements = true;

    public void Close() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    public ClientForm2() {
        super(Main.getLocalString("Client"));
        setContentPane(panel1);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //MyListRenderer.alpha = 0;
        //MyListRenderer.toChangeAlpha.add(0);

        startButton.setText(Main.getLocalString("Start"));
        stopButton.setText(Main.getLocalString("Stop"));
        useFiltersCheckBox.setText(Main.getLocalString("Use Filters"));
        updateCollectionButton.setText(Main.getLocalString("Update"));
        color.setText(Main.getLocalString("Color"));
        filters.setText(Main.getLocalString("Filters"));
        yearf.setText(Main.getLocalString("Year"));
        country.setText(Main.getLocalString("Country"));


        list1.setCellRenderer(new MyListRenderer());

        Timer timer = new Timer(100, (e) -> {
            if (!collectionDrawn) {
                collectionDrawn = true;
                ((DefaultListModel) list1.getModel()).clear();
                MyListRenderer.itemColors.clear();

                Stream.of(umbrellasArray).forEach((u) -> {
//                    if (shouldFilter(u))
//                        list1.getCellRenderer().getListCellRendererComponent()
                    MyListRenderer.itemColors.add(((Umbrella) u).getColor());
                    ((DefaultListModel) list1.getModel()).addElement(u.toString());
                });
            }
        });
        timer.start();

        final int TIMER_DELAY = 50;
        Timer hideTimer = new Timer(TIMER_DELAY, (e) -> {
            if (isHidingElements)
                MyListRenderer.alpha = Math.max(0, MyListRenderer.alpha - 19);
            else
                MyListRenderer.alpha = Math.min(255, MyListRenderer.alpha + 15);

            if (MyListRenderer.alpha == 0 && isHidingElements) {
                isHidingElements = false;
            }

            list1.updateUI();
        });

        updateCollectionButton.addActionListener((e) -> {

            final int WAIT_TIME = 5000;
            Thread mainTask = new Thread() {
                public void run() {
                    MyListRenderer.alpha = 255;
                    resAnswer(sentAnswer());
                }
            };
            mainTask.start();
            new java.util.Timer(true).schedule(new TimerTask() {
                @Override
                public void run() {
                    if (mainTask.isAlive()) {
                        mainTask.interrupt();
                        System.out.println("Server doesn't answer");
                    }
                }
            }, WAIT_TIME);

        });

        list1.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                JList l = (JList) e.getSource();
                ListModel m = l.getModel();
                int index = l.locationToIndex(e.getPoint());
                if (index > -1) {
                    l.setToolTipText(m.getElementAt(index).toString());
                }
            }
        });

        inputColor.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                update();
            }

            public void removeUpdate(DocumentEvent e) {
                update();
            }

            public void insertUpdate(DocumentEvent e) {
                update();
            }

            void update() {
                Color c = strToColor(inputColor.getText());
                if (c == null)
                    inputColor.setBackground(Color.RED);
                else
                    inputColor.setBackground(Color.green);
            }
        });

        startButton.addActionListener((e) -> {
            MyListRenderer.alpha = 255;
            MyListRenderer.toChangeAlpha.clear();

            for (int i = 0; i < umbrellasArray.length; i++) {
                if (shouldFilter(umbrellasArray[i]))
                    MyListRenderer.toChangeAlpha.add(i);
            }

            isHidingElements = true;
            hideTimer.start();
        });

        stopButton.addActionListener((e) -> {
            hideTimer.stop();
        });

        comboBox1.setSelectedIndex((int) Main.resourceBundle.getObject("langIndex"));
        comboBox1.addActionListener(e -> {
            String selStr = comboBox1.getSelectedItem().toString();
            Locale loc = null;
            switch (selStr) {
                case "Русский":
                    loc = new Locale("ru", "RU");
                    break;
                case "Белорусский":
                    loc = new Locale("be", "BY");
                    break;
                case "Греческий":
                    loc = new Locale("el", "GR");
                    break;
                case "Испанский(Никарагуа)":
                    loc = new Locale("es", "NI");
                    break;
            }
            Main.formWithLocale(loc);
        });

    }

    private Color strToColor(String str) {
        String[] parts = inputColor.getText().split(";");
        if (parts.length != 3) {
            return null;
        }

        int r, g, b;

        try {
            r = Integer.parseInt(parts[0]);
            g = Integer.parseInt(parts[1]);
            b = Integer.parseInt(parts[2]);

        } catch (Exception e) {
            return null;
        }

        if (isVal(r) && isVal(g) && isVal(b))
            return new Color(r, g, b);
        else
            return null;
    }

    private Boolean isVal(int x) {
        return (x >= 0 && x <= 255);
    }

    boolean shouldFilter(Object objUmbrella) {
        if (!useFiltersCheckBox.isSelected())
            return false;

        Umbrella u = (Umbrella) objUmbrella;
        Color c = strToColor(inputColor.getText());
//        if (!u.getColor().equals(c))
//            return false;
//
//        if (!u.getManufacturer().equals(inputCountry.getText()))
//            return false;
//        if (u.getGr().get(Calendar.YEAR) != (slider1.getValue()))
//            return false;

        return (u.getColor().equals(c)) | (u.getManufacturer().equals(inputCountry.getText())) | (u.getDateTime().getYear() == (slider1.getValue()));
    }

    public void resAnswer(DatagramChannel datagramChannel) {
        try {
            byte[] sizeBytes = new byte[4];
            ByteBuffer sizeBuffer = ByteBuffer.wrap(sizeBytes);
            sizeBuffer.clear();

            datagramChannel.receive(sizeBuffer);


            sizeBuffer.flip();
            int size = sizeBuffer.getInt();

            byte[] readBytes = new byte[size];
            ByteBuffer readBuffer = ByteBuffer.wrap(readBytes);
            datagramChannel.receive(readBuffer);
            readBuffer.flip();

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(readBytes);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Object of = objectInputStream.readObject();
            Object[] umbrellas = (Object[]) of;
            umbrellasArray = umbrellas;
            Arrays.sort(umbrellasArray);
            collectionDrawn = false;
//            ((DefaultListModel) list1.getModel()).clear();
//            System.out.println("Clear");
//            Stream.of(umbrellas).forEach((u) -> {
//                //((DefaultListModel) list1.getModel()).addElement(" ");
//                ((DefaultListModel) list1.getModel()).addElement(u.toString());
//                System.out.println("Add");
//            });
            //System.out.println(((DefaultListModel) list1.getModel()).get(0));
        } catch (IOException e) {
            e.getMessage();
        } catch (ClassNotFoundException e) {
            e.getMessage();
        }

    }


    public DatagramChannel sentAnswer() {
        String ADDR = "localhost";
        final int PORT = 57912;
        byte b[] = {1};
        SocketAddress socketAddress = new InetSocketAddress(ADDR, PORT);
        DatagramChannel datagramChannel = null;
        try {
            datagramChannel = DatagramChannel.open();
            datagramChannel.bind(null);
            ByteBuffer byteBuffer = ByteBuffer.wrap(b);
            //byteBuffer.flip();


            datagramChannel.send(byteBuffer, socketAddress);
            //datagramChannel.socket().setSoTimeout(1000);

            byteBuffer.clear();
            System.out.println("Sent " + b.length + " bytes");
        } catch (IOException e) {
            e.getMessage();
        }

        return datagramChannel;
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridBagLayout());
        panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 4, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setForeground(new Color(-4493816));
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel2.add(panel1, gbc);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel3, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(6, 2, new Insets(0, 0, 0, 0), -1, 20));
        panel4.setBackground(new Color(-4493816));
        Font panel4Font = this.$$$getFont$$$("Caladea", Font.BOLD | Font.ITALIC, -1, panel4.getFont());
        if (panel4Font != null) panel4.setFont(panel4Font);
        panel4.setForeground(new Color(-4490990));
        panel1.add(panel4, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        color = new JLabel();
        Font colorFont = this.$$$getFont$$$("Consolas", Font.BOLD | Font.ITALIC, -1, color.getFont());
        if (colorFont != null) color.setFont(colorFont);
        color.setForeground(new Color(-4474699));
        color.setText("Color");
        panel4.add(color, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        filters = new JLabel();
        filters.setBackground(new Color(-4493816));
        Font filtersFont = this.$$$getFont$$$("Caladea", Font.BOLD | Font.ITALIC, 18, filters.getFont());
        if (filtersFont != null) filters.setFont(filtersFont);
        filters.setForeground(new Color(-4474699));
        filters.setText("Filters");
        panel4.add(filters, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        country = new JLabel();
        Font countryFont = this.$$$getFont$$$("Consolas", Font.BOLD | Font.ITALIC, -1, country.getFont());
        if (countryFont != null) country.setFont(countryFont);
        country.setForeground(new Color(-4474699));
        country.setText("Country");
        panel4.add(country, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        yearf = new JLabel();
        Font yearfFont = this.$$$getFont$$$("Consolas", Font.BOLD | Font.ITALIC, -1, yearf.getFont());
        if (yearfFont != null) yearf.setFont(yearfFont);
        yearf.setForeground(new Color(-4474699));
        yearf.setText("Year");
        panel4.add(yearf, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        inputCountry = new JTextField();
        inputCountry.setBackground(new Color(-4474699));
        inputCountry.setForeground(new Color(-16514304));
        inputCountry.setText("");
        panel4.add(inputCountry, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        inputColor = new JTextField();
        inputColor.setBackground(new Color(-4474699));
        inputColor.setForeground(new Color(-16514304));
        inputColor.setText("");
        panel4.add(inputColor, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        useFiltersCheckBox = new JCheckBox();
        useFiltersCheckBox.setBackground(new Color(-4493816));
        useFiltersCheckBox.setText("Use filters");
        panel4.add(useFiltersCheckBox, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        updateCollectionButton = new JButton();
        updateCollectionButton.setBackground(new Color(-4493816));
        updateCollectionButton.setForeground(new Color(-4474699));
        updateCollectionButton.setHideActionText(false);
        updateCollectionButton.setText("Update collection");
        panel4.add(updateCollectionButton, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        slider1 = new JSlider();
        slider1.setBackground(new Color(-4493816));
        slider1.setMajorTickSpacing(19);
        slider1.setMaximum(2018);
        slider1.setMinimum(1999);
        slider1.setMinorTickSpacing(1);
        slider1.setPaintLabels(true);
        slider1.setPaintTicks(true);
        slider1.setPaintTrack(true);
        slider1.setSnapToTicks(false);
        slider1.setValueIsAdjusting(false);
        panel4.add(slider1, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, 200));
        panel5.setBackground(new Color(-4493816));
        panel1.add(panel5, new com.intellij.uiDesigner.core.GridConstraints(2, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        list1 = new JList();
        list1.setBackground(new Color(-4493816));
        list1.setForeground(new Color(-16514304));
        final DefaultListModel defaultListModel1 = new DefaultListModel();
        list1.setModel(defaultListModel1);
        list1.setSelectionBackground(new Color(-4476917));
        list1.setSelectionForeground(new Color(-16514304));
        panel5.add(list1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(300, 200), null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        panel5.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel6, new com.intellij.uiDesigner.core.GridConstraints(2, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        startButton = new JButton();
        startButton.setText("Start");
        panel1.add(startButton, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        stopButton = new JButton();
        stopButton.setText("Stop");
        panel1.add(stopButton, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBox1 = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("Русский");
        defaultComboBoxModel1.addElement("Белорусский");
        defaultComboBoxModel1.addElement("Греческий");
        defaultComboBoxModel1.addElement("Испанский(Никарагуа)");
        comboBox1.setModel(defaultComboBoxModel1);
        panel1.add(comboBox1, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel spacer2 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel2.add(spacer2, gbc);
        final JPanel spacer3 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel2.add(spacer3, gbc);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }
}
