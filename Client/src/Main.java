import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.sql.Array;
import java.util.Scanner;
import java.util.stream.Stream;

public class Main {
    public static void main (String args[]){


            Scanner scanner = new Scanner(System.in);

            while (true) {

                String input = scanner.nextLine();

                String[] words = input.split(" ");
                //if (words.length>1)

                switch (words[0]) {
                    case "info":
                        //Scommands.info();
                        String ADDR = "localhost";
                        final int PORT= 57919;
                        byte b [] = {1};
                        SocketAddress socketAddress = new InetSocketAddress(ADDR,PORT);
                        try {
                            DatagramChannel datagramChannel = DatagramChannel.open();
                            datagramChannel.bind(null);
                            ByteBuffer byteBuffer = ByteBuffer.wrap(b);
                            //byteBuffer.flip();
                            datagramChannel.send(byteBuffer,socketAddress);
                            byteBuffer.clear();
                            System.out.println("Sent " + b.length + " bytes");

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
                            Object[] umbrellas = (Object[])of;
                            Stream.of(umbrellas).forEach((u) -> {
                                System.out.println(u.toString());
                            });

                        }catch (Exception e){

                            e.printStackTrace();
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
