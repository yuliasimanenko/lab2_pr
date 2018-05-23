import lab1.Umbrella;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.TimerTask;
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
    private JPanel Client;

    Object[] umbrellasArray = new Object[0];
    boolean collectionDrawn = false;
    ArrayList<Object> objsToHide = new ArrayList<>();
    boolean isHidingElements = true;

    public ClientForm2() {
        super("Client");
        setContentPane(panel1);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //MyListRenderer.alpha = 0;
        //MyListRenderer.toChangeAlpha.add(0);

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

        final int TIMER_DELAY = 125;
        Timer hideTimer = new Timer(TIMER_DELAY, (e) -> {
            if (isHidingElements)
                MyListRenderer.alpha = Math.max(0, MyListRenderer.alpha - 6);
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

        if (!u.getManufacturer().equals(inputCountry.getText()))
            return false;
        if (u.getGr().get(Calendar.YEAR) != (slider1.getValue()))
            return false;

        return u.getColor().equals(c);
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

}
