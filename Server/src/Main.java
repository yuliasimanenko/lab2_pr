import lab1.CollectionManager;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Scanner;

public class Main {
    static ServerForm form;

    public static void main(String args[]) {
        // System.out.print("разница" );
        String fileName = "collection.txt";
        if (args.length > 0)
            fileName = args[0];
// на крайний случай обрушения
        Scanner scanner = new Scanner(System.in);
        CollectionManager commands = new CollectionManager(fileName);
        Thread mainThread = Thread.currentThread();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            commands.saveToFile("collection.txt");
            System.out.println("Закрываем программу");
            try {
                mainThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));


//обслуживание запросов пользователя
        NetworkServer server = new NetworkServer(commands);
        Thread tr = new Thread(server::runServer);
        tr.start();


        showForm(commands);

//менять коллекцию на сервере
//        while (true) {
//
//            String input = scanner.nextLine();
//
//            String[] words = input.split(" ");
//            //if (words.length>1)
//
//            switch (words[0]) {
//                case "info":
//                    commands.info();
//                    break;
//                case "add_if_min":
//                    try {
//                        String element = input.substring("add_if_min ".length());
//                        commands.add_if_min(element);
//                    } catch (StringIndexOutOfBoundsException e) {
//                        System.out.println("error");
//                    }
//                    break;
//                case "import":
//                    try {
//                        String address = input.substring("import ".length());
//                        try {
//                            commands.importCommand(address);
//                        } catch (Exception e) {
//
//                            e.printStackTrace();
//                        }
//                    } catch (Exception e) {
//                        System.out.print("Аргумент не введен");
//                    }
//                    break;
//                case "remove_greater":
//                    try {
//                        String example = input.substring("remove_greater ".length());
//                        commands.remove_greater(example);
//                    } catch (Exception e) {
//                        System.out.println("Аргумент не введен");
//                    }
//                    break;
//                //case "story":
//                //    lab1.Story.story(commands);
//                //    break;
//            }
//            //System.out.println(words[0]);
//        }

    }

    private static void showForm(CollectionManager manager) {
        form = new ServerForm(manager);
        form.setResizable(false);

        JMenuBar menuBar;
        JMenu menu1, menu2, menu3;
        JMenuItem menuItem;


        //Create the menu bar.
        menuBar = new JMenuBar();

//Build the first menu.
        menu1 = new JMenu("Save");
        menu1.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                manager.saveToFile("collection.txt");
                System.out.println("save");
            }

            @Override
            public void menuDeselected(MenuEvent e) {

            }

            @Override
            public void menuCanceled(MenuEvent e) {

            }
        });
//        menu1.getAccessibleContext().setAccessibleDescription(
//                "The only menu in this program that has menu items");
        menuBar.add(menu1);

        menu2 = new JMenu("Add");

        menuBar.add(menu2);
        menu3 = new JMenu("Delete");
        menuBar.add(menu3);



//a group of JMenuItems
//        menuItem = new JMenuItem("A text-only menu item",
//                KeyEvent.VK_T);
//        menuItem.setAccelerator(KeyStroke.getKeyStroke(
//                KeyEvent.VK_1, ActionEvent.ALT_MASK));
//        menuItem.getAccessibleContext().setAccessibleDescription(
//                "This doesn't really do anything");
//        menu.add(menuItem);

        form.setJMenuBar(menuBar);

        form.pack();
        form.setVisible(true);
    }

}
