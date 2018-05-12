import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.ObjectInputStream;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.sql.Array;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;
import java.util.TimerTask;
import java.util.Timer;

public class Main {
    private static final int WAIT_TIME = 5000;

    public static void main(String args[]) {
        ClientForm2 form = new ClientForm2();
        form.pack();
        form.setVisible(true);
    }

    public static void main_old (String args[]){

            Scanner scanner = new Scanner(System.in);

            while (true) {

                String input = scanner.nextLine();

                String[] words = input.split(" ");
                //if (words.length>1)

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
