package lab1;

import java.util.Scanner;

public class lab1 {

	public static void main(String[] args) {
	    String fileName = "collection.txt";
	    if (args.length > 0)
	        fileName = args[0];

        Scanner scanner = new Scanner(System.in);
        CollectionManager commands = new CollectionManager(fileName);
        Story.story();
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
        while (true) {

            String input = scanner.nextLine();

            String[] words = input.split(" ");
            //if (words.length>1)

            switch (words[0]) {
                case "info":
                    commands.info();
                    break;
                case "add_if_min":
                    try{
                    String element = input.substring("add_if_min ".length());
                    commands.add_if_min(element);}catch (StringIndexOutOfBoundsException e){
                        System.out.println("error");
                    }
                    break;
                case "import":
                    try{
                    String address = input.substring("import ".length());
                    try{
                        commands.importCommand(address);
                    }
                    catch(Exception e){

                        e.printStackTrace();
                    }}catch (Exception e){
                       System.out.print( "Аргумент не введен");
                    }
                    break;
                case "remove_greater":
                    try{
                    String example = input.substring("remove_greater ".length());
                    commands.remove_greater(example);}catch (Exception e ){
                        System.out.println("Аргумент не введен");
                    }
                    break;
                case "story":
                    Story.story();
                    break;
            }
            //System.out.println(words[0]);
        }

    }
}