import lab1.Story;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.*;
import java.util.stream.Stream;

public class Main {
    private static final int WAIT_TIME = 5000;

     static ResourceBundle resourceBundle;
     static ClientForm2 form = null;

    public static void main(String args[]) {
        Locale curLocale = new Locale("ru", "RU");
        formWithLocale(curLocale);
        //testORM();
    }

//    public static void testORM() {
//
//        Umbrella u = new Umbrella(new Color(5, 5, 5), "Sudan", LocalDateTime.now());
//        Umbrella u2 = new Umbrella(new Color(5, 5, 5), "Denmark", LocalDateTime.now().minusYears(2).minusMonths(3));
//
//        ORMClass<Umbrella> orm = new ORMClass<>(Umbrella.class);
//        orm.create();
//        orm.add(u);
//        orm.remove(u);
//        orm.replace(u, u2);
//        orm.get("manufacturer like \"China\"");
//        orm.printSQL();
//    }

    public static void formWithLocale(Locale locale) {
        if (form != null)
            form.Close();

        resourceBundle = ResourceBundle.getBundle("Resources", locale);

        form = new ClientForm2();
        form.setResizable(false);
        form.pack();
        form.setVisible(true);
    }

    public static String getLocalString(String keyStr){
        return resourceBundle.getString(keyStr);
    }

    public static void main_old (String args[]){

            Scanner scanner = new Scanner(System.in);

            while (true) {

                String input = scanner.nextLine();

                String[] words = input.split(" ");
                
                switch (words[0]) {
                    case "info":
                       final   boolean[] interrupted = {false};
                        Thread mainTask = new Thread(){
                            public void run(){
                                resAnswer(sentAnswer());
                            }
                        };
                        mainTask.start();
                        new Timer(true).schedule(new TimerTask() {
                            @Override
                            public void run() {
                                mainTask.interrupt();
                                interrupted[0] = true;

                            }
                        }, WAIT_TIME);
                        if(interrupted[0]){
                            System.out.println("Time error, connection is lost");
                        }

                                //sentAnswer();
                                //resAnswer(sentAnswer());

                        break;
                    case "story":
                        Story.story();
                        break;
                }

                //System.out.println(words[0]);
            }

    }

    public static void resAnswer(DatagramChannel datagramChannel){
        try{
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
            Stream.of(umbrellas).forEach((u) -> {
                System.out.println(u.toString());
            });
        }catch (IOException e){
            e.getMessage();
        }catch (ClassNotFoundException e){
            e.getMessage();
        }

    }


    public static DatagramChannel sentAnswer(){
        String ADDR = "localhost";
        final int PORT= 57912;
        byte b [] = {1};
        SocketAddress socketAddress = new InetSocketAddress(ADDR,PORT);
        DatagramChannel datagramChannel= null;
        try{
            datagramChannel = DatagramChannel.open();
            datagramChannel.bind(null);
            ByteBuffer byteBuffer = ByteBuffer.wrap(b);
            //byteBuffer.flip();


            datagramChannel.send(byteBuffer, socketAddress);
            //datagramChannel.socket().setSoTimeout(1000);

            byteBuffer.clear();
            System.out.println("Sent " + b.length + " bytes");
        }catch (IOException e ){
            e.getMessage();
        }

        return datagramChannel;
    }
}
