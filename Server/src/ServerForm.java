
import lab1.CollectionManager;
import lab1.Umbrella;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.lang.reflect.Array;
import java.util.Calendar;

public class ServerForm extends JFrame {
    private JPanel panel1;
    private JPasswordField passwordField1;
    private JButton logInButton;
    private JButton add_if_minButton;
    private JButton infoButton;
    private JButton importcommandButton;
    private JButton remove_greaterButton;
    private JTable table1;

    public ServerForm(CollectionManager manager) {
        super("Server");
        setContentPane(panel1);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        add_if_minButton.setEnabled(false);
        infoButton.setEnabled(false);
        importcommandButton.setEnabled(false);
        remove_greaterButton.setEnabled(false);

        logInButton.addActionListener((e) -> {
            String password = String.valueOf(passwordField1.getPassword());
            if (password.equals("parol")) {
                // System.out.println("true");
                add_if_minButton.setEnabled(true);
                infoButton.setEnabled(true);
                importcommandButton.setEnabled(true);
                remove_greaterButton.setEnabled(true);

            }
        });

        infoButton.addActionListener((e) -> {
            table1.removeAll();
            DefaultTableModel dtm = new DefaultTableModel(new Object[][]{},
                    new String[]{"color_RGB", "year", "country"});
            table1.setModel(dtm);

            Object[] tableObjects = manager.getSortedUmbrellas();
            for (int i = 0; i < tableObjects.length; i++) {
                Umbrella u = (Umbrella) tableObjects[i];
                dtm.addRow(new Object[]{u.getColor().toString(), u.getGr().get(Calendar.YEAR), u.getManufacturer()});
            }

            //for (int i = 0; i < 100; i++)
            //  dtm.addRow(new Object[]{"a", "b", "c", "d", "e", "f"});

            //table1.addColumn(new TableColumn());
        });
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
        panel1 = new JPanel();
        panel1.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        panel1.setPreferredSize(new Dimension(900, 371));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 50));
        panel1.add(panel2);
        passwordField1 = new JPasswordField();
        passwordField1.setColumns(25);
        passwordField1.setName("Your Password");
        panel1.add(passwordField1);
        logInButton = new JButton();
        logInButton.setText("Log in");
        panel1.add(logInButton);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new FlowLayout(FlowLayout.CENTER, 90, 5));
        panel1.add(panel3);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel4.setMaximumSize(new Dimension(180, 32767));
        panel1.add(panel4);
        add_if_minButton = new JButton();
        add_if_minButton.setHideActionText(false);
        add_if_minButton.setHorizontalAlignment(0);
        add_if_minButton.setMaximumSize(new Dimension(200, 41));
        add_if_minButton.setMinimumSize(new Dimension(0, 41));
        add_if_minButton.setOpaque(true);
        add_if_minButton.setPreferredSize(new Dimension(200, 41));
        add_if_minButton.setText("add_if_min");
        panel4.add(add_if_minButton, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        infoButton = new JButton();
        infoButton.setText("info");
        panel4.add(infoButton, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        importcommandButton = new JButton();
        importcommandButton.setText("importcommand");
        panel4.add(importcommandButton, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        remove_greaterButton = new JButton();
        remove_greaterButton.setText("remove_greater");
        panel4.add(remove_greaterButton, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setPreferredSize(new Dimension(453, 200));
        panel1.add(scrollPane1);
        table1 = new JTable();
        scrollPane1.setViewportView(table1);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }
}

